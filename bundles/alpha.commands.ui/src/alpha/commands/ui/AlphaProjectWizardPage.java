package alpha.commands.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class AlphaProjectWizardPage extends WizardPage {
	
	private Text _projectName;

	public AlphaProjectWizardPage(String pageName, String description) {
		super(pageName);
		setTitle(pageName);
		setDescription(description);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		Label label = new Label(container, SWT.NULL);
		label.setText("&Project name:");

		_projectName = new Text(container, SWT.BORDER | SWT.SINGLE);
		_projectName.setText(getDefaultProjectName());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		_projectName.setLayoutData(gd);
		_projectName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
	
		dialogChanged();
		setControl(container);
	}
	
	private void dialogChanged() {

		String projectName = _projectName.getText();
		if (projectName.length() == 0) {
			updateStatus("Project name must be specified");
			return;
		}
		if (projectName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
	
		updateStatus(null);
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}
	
	public String getProjectName(){
		return _projectName.getText();
	}
	
	protected String getDefaultProjectName() {
		return "";
	}
}

