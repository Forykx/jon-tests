/**
 * @author lzoubek@redhat.com (Libor Zoubek)
 * Jun 25, 2013
 */

/**
 * This test removes bundle (first by removing it's versions) then the bundle itself
 */
verbose = 10;

var b = bundles.find()
assertTrue(b.length == 1, "New bundle was returned from server");

var bundle = b[0];
assertTrue(bundle.versions().length == 2, "2 Bundle versions were uploaded");
assertTrue(bundle.destinations().length == 1, "1 Bundle versions were created");

bundle.versions().forEach(function(b) {
	assertTrue(b.files().length == 2,"There are 2 files within bundle version");
	b.remove();
});
assertTrue(bundle.versions().length == 0, "All versions were removed");
bundle.remove();
assertTrue(bundles.find().length == 0,"Bundle was removed from server");



