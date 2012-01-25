package com.redhat.qe.jon.sahi.tests.plugins.eap6.domain;

import com.redhat.qe.jon.sahi.tasks.Navigator.InventoryNavigation;
import com.redhat.qe.jon.sahi.tests.plugins.eap6.AS7PluginSahiTasks.Navigate;
import com.redhat.qe.auto.testng.Assert;
import com.redhat.qe.jon.sahi.tests.plugins.eap6.AS7PluginSahiTasks;
import com.redhat.qe.jon.sahi.tests.plugins.eap6.AS7PluginSahiTestScript;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Jan Martiska (jmartisk@redhat.com)
 * @see TCMS test case 97550
 * @since 16 September 2011
 * 
 */
public class AutodetectConfigurationTest extends AS7PluginSahiTestScript {                                
    
    @BeforeClass(groups="autodetectConfiguration")
    protected void setupEapPlugin() {        
        as7SahiTasks = new AS7PluginSahiTasks(sahiTasks);
    }
    
    @Test(groups={"autodetectConfiguration"})
    public void autodetectConfiguration() {
    	InventoryNavigation nav = new InventoryNavigation(System.getProperty("agent.name"), "Inventory", System.getProperty("as7.domain.controller.name"));
    	
    	as7SahiTasks.uninventorizeResourceByNameIfExists(System.getProperty("agent.name"), System.getProperty("as7.domain.controller.name"));
        as7SahiTasks.performManualAutodiscovery(System.getProperty("agent.name"));

        sahiTasks.assertResourceExists(true, nav.pathPush("as7.domain.host.server-one.name"));
        
    }    
    
    
    
}
