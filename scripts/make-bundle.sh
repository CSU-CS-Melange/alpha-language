#!/bin/bash

# makes an eclipse bundle with AlphaZ and all required dependencies

if [[ -z $1 ]]; then
    echo "usage: $0 OS"
    exit 1
fi

base_url="https://www.eclipse.org/downloads/download.php?file=/eclipse/downloads/drops4/R-4.24-202206070700"

if [[ $1 == "linux-x86" ]]; then
    OS="linux"
    eclipse_url="$base_url/eclipse-SDK-4.24-linux-gtk-x86_64.tar.gz"
    suffix="tar.gz"
elif [[ $1 == "mac-x86" ]]; then
    OS="mac"
    eclipse_url="$base_url/eclipse-SDK-4.24-macosx-cocoa-x86_64.dmg"
    suffix="dmg"
elif [[ $1 == "mac-aarch64" ]]; then
    OS="mac"
    eclipse_url="$base_url/eclipse-SDK-4.24-macosx-cocoa-aarch64.dmg"
    suffix="dmg"
else
    echo "error: unsupported OS type"
    exit 1
fi
version=$1

# this is passed by the action runner
if [[ -n "$GITHUB_SHA" ]]; then
    IN_ACTION_RUNNER=1
fi

rm -rf work
mkdir -p work

if [[ $OS == "linux" ]]; then
    wget -O eclipse.tar.gz $eclipse_url
    tar -xzf eclipse.tar.gz

    eclipse_instance="./eclipse/eclipse"

    # install GeCoS & Xtext plugins
    bash ./scripts/install-plugins.sh $eclipse_instance scripts/resources/required-plugins.p2f
    
    # install AlphaZ plugin
    if [[ -n "$IN_ACTION_RUNNER" ]]; then
        $eclipse_instance \
            -nosplash \
            -application org.eclipse.equinox.p2.director \
            -repository file:///home/runner/work/alpha-language/alpha-language/releng/alpha.language.update/target/repository \
            -installIU alpha.language.feature.feature.group
    else
        bash ./scripts/install-plugins.sh $eclipse_instance scripts/resources/alpha-language-plugins-master.p2f
    fi
    
    tar -czf eclipse-alpha-language-$version.tar.gz eclipse
fi

if [[ $OS == "mac" ]]; then
    mkdir -p eclipse-download-$version
    if [[ ! -f eclipse-download-$version/eclipse.dmg ]]; then
        wget -O eclipse-download-$version/eclipse.dmg $eclipse_url
    fi
    hdiutil attach -nobrowse -mountpoint eclipse-mnt eclipse-download-$version/eclipse.dmg 
    rm -rf alpha-language-bundle/
    mkdir alpha-language-bundle/
    cp -R eclipse-mnt/Eclipse.app alpha-language-bundle/
    hdiutil detach eclipse-mnt

    eclipse_instance="./alpha-language-bundle/Eclipse.app/Contents/MacOS/eclipse"

    # install GeCoS & Xtext plugins
    bash ./scripts/install-plugins.sh $eclipse_instance scripts/resources/required-plugins.p2f
    
    # install AlphaZ plugin
    if [[ -n "$IN_ACTION_RUNNER" ]]; then
        $eclipse_instance \
            -nosplash \
            -application org.eclipse.equinox.p2.director \
            -repository file:///home/runner/work/alpha-language/alpha-language/releng/alpha.language.update/target/repository \
            -installIU alpha.language.feature.feature.group
    else
        bash ./scripts/install-plugins.sh $eclipse_instance scripts/resources/alpha-language-plugins-master.p2f
    fi
    # create eclipse disk image file
    rm -f "eclipse-alpha-language-$version.dmg"
    # sign the disk image if in action runner
    if [[ -n "$IN_ACTION_RUNNER" ]]; then
        eclipse_app="./alpha-language-bundle/Eclipse.app"
        signing_identity=`security find-identity -v -p codesigning | cut -d' ' -f 4`
        /usr/bin/codesign --force -s $signing_identity $eclipse_app -v
    fi
    # add a symlink for the dmg and create the disk image
    ln -s /Applications alpha-language-bundle/Applications
    hdiutil create -fs HFS+ -srcfolder alpha-language-bundle -volname "eclipse-alpha-language-$version" "eclipse-alpha-language-$version.dmg"

    # clean up
    rm -rf alpha-language-bundle eclipse-download-$version

fi

echo "created eclipse-alpha-language-$version.$suffix"
