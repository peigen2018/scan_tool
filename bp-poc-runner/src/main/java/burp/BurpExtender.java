package burp;


import com.pg.burpextender.component.PocRunner;
import com.pg.burpextender.view.MainView;

public class BurpExtender implements IBurpExtender  {

    private static final String EXTENDER_NAME = "POC Runner";


    @Override
    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks)
    {
        MainView view = new MainView();
        callbacks.setExtensionName(EXTENDER_NAME);
        PocRunner factory = new PocRunner();
        callbacks.registerContextMenuFactory(factory);
        callbacks.addSuiteTab(factory);
    }
}
