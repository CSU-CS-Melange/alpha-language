#!/bin/bash

readme_file=./releng/alpha.language.update/target/repository/README.md

cat > $readme_file <<EOF
This endpoint acts as the Eclipse update site used for the alpha-language repository. Last generated on \``date`\` from commit \``echo $GITHUB_SHA`\`.

## Eclipse bundles

$(tree ./releng/alpha.language.update/target/repository)

EOF
