package com.redhat.qe.jon.clitest.tests.bundles;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.redhat.qe.jon.clitest.base.CliEngine;
import com.redhat.qe.jon.clitest.base.CliTestRunner;

/**
 * Tests creating Bundle from distribution file via local file and
 * goes by whole work-flow: deploys, upgrades, reverts, purges and removes it.
 * 
 * @author lzoubek
 * 
 */
public class BundleWorkflowTest extends CliEngine {

    
    /**
     * by default all our JS tests get groupName parameter and depend on rhqapi.js
     */
    @Override
    public CliTestRunner createJSRunner(String jsFile) {
	return super.createJSRunner(jsFile)
	.addDepends("/js-files/rhqapi.js")
	.withArg("groupName", "my-destination");
    }
    /**
     * this is a must for running tests - we upload 2 versions of same bundle to server,
     * create compatible group of all platforms + bundle destination pointing to this group
     */
    @BeforeClass
    public void initBundles() {
	createJSRunner("bundles/init.js")
	.addDepends("/js-files/rhqapi.js")
	.resourceSrcs("/bundles/bundle.zip","/bundles/bundle-v2.zip")
	.resourceDests("/tmp/bundle.zip","/tmp/bundle-v2.zip")
	.withArg("bundle1", "/tmp/bundle.zip")
	.withArg("bundle2", "/tmp/bundle-v2.zip").run();	
    }

    @Test()
    public void deploy() {
	createJSRunner("bundles/deployBundle.js")
	.addExpect("status : Success")
	.withArg("version", "1.0").run();
    }
    
    @Test(dependsOnMethods="deploy")
    public void upgrade() {
	createJSRunner("bundles/deployBundle.js")
	.addExpect("status : Success")
	.withArg("version", "2.0").run();
    }
    
    @Test(dependsOnMethods="upgrade")
    public void revert() {
	createJSRunner("bundles/revertBundle.js")
	.addExpect("status : Success")
	.run();
    }
    @Test(dependsOnMethods="revert")
    public void purge() {
	createJSRunner("bundles/purgeBundle.js")
	.run();
    }
    @Test(dependsOnMethods="purge")
    public void remove() {
	createJSRunner("bundles/removeBundle.js")
	.run();
    }

}