import junit.framework.TestCase;
import models.OperationalSystem;
import models.Process;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import mainpack.Main;
import models.Resource;
import models.Process;

import static org.junit.Assert.*;

/**
 * Created by José Lucas on 08/08/2016.
 */
public class OperationalSystemTest {
    @Test
    public void safeSituationTest() throws Exception{
        int numberOfResources = 2;

        ArrayList<Integer> existingResources = new ArrayList<Integer>(Collections.nCopies(2, 0));// instanciate it with resources.size() position populated with 0

        existingResources.set(0,2); // id,quant of resource
        existingResources.set(1,2);


        ArrayList<Integer> availableResources = new ArrayList<Integer>(Collections.nCopies(2, 0));// instanciate it with resources.size() position populated with 0
        availableResources.set(0,0); // id, AvailableQuant
        availableResources.set(1,2);


        ArrayList<ArrayList<Integer>> allocatedResources = new ArrayList<ArrayList<Integer>>();

        //process One
        allocatedResources.add(new ArrayList<Integer>(Collections.nCopies(numberOfResources, 0)));//allocates the space of max number of resources
        allocatedResources.get(0).set(0, 1);// process one has 1 instance of resource 0
        allocatedResources.get(0).set(1, 0);// process one has 2 instance of resource 1

        //process Two
        allocatedResources.add(new ArrayList<Integer>(Collections.nCopies(numberOfResources, 0)));//allocates the space of max number of resources
        allocatedResources.get(1).set(0, 1);// process two has 1 instance of resource 0
        allocatedResources.get(1).set(1, 0);// process two has 0 instance of resource 1



        ArrayList<ArrayList<Integer>> requestingResources = new ArrayList<ArrayList<Integer>>();
        //process One
        requestingResources.add(new ArrayList<Integer>(Collections.nCopies(numberOfResources, 0)));//allocates the space of max number of resources
        requestingResources.get(0).set(0, 0);// process one is requesting one instance of resource 0
        requestingResources.get(0).set(1, 1);// process one is requesting 0 instance of resource 1

        //process Two
        requestingResources.add(new ArrayList<Integer>(Collections.nCopies(numberOfResources, 0)));//allocates the space of max number of resources
        requestingResources.get(1).set(0, 0);// process two is requesting 1 instance of resource 0
        requestingResources.get(1).set(1, 1);// process two is requesting 0 instance of resource 1

        OperationalSystem os = new OperationalSystem(3000);
        boolean deadlock = os.checkDeadLock(existingResources, availableResources, allocatedResources, requestingResources).size() > 0;
        Assert.assertFalse("ERROR !!! This situation must not generate deadlock",deadlock);
    }

    @Test
    public void deadLockSituationTest() throws Exception{

        int numberOfResources = 2;

        ArrayList<Integer> existingResources = new ArrayList<Integer>(Collections.nCopies(2, 0));// instanciate it with resources.size() position populated with 0

        existingResources.set(0,2); // id,quant of resource
        existingResources.set(1,2);


        ArrayList<Integer> availableResources = new ArrayList<Integer>(Collections.nCopies(2, 0));// instanciate it with resources.size() position populated with 0
        availableResources.set(0,0); // id, AvailableQuant
        availableResources.set(1,0);


        ArrayList<ArrayList<Integer>> allocatedResources = new ArrayList<ArrayList<Integer>>();

        //process One
        allocatedResources.add(new ArrayList<Integer>(Collections.nCopies(numberOfResources, 0)));//allocates the space of max number of resources
        allocatedResources.get(0).set(0, 1);// process one has 1 instance of resource 0
        allocatedResources.get(0).set(1, 2);// process one has 2 instance of resource 1

        //process Two
        allocatedResources.add(new ArrayList<Integer>(Collections.nCopies(numberOfResources, 0)));//allocates the space of max number of resources
        allocatedResources.get(1).set(0, 1);// process two has 1 instance of resource 0
        allocatedResources.get(1).set(1, 0);// process two has 0 instance of resource 1

        ArrayList<ArrayList<Integer>> requestingResources = new ArrayList<ArrayList<Integer>>();


        //process One
        requestingResources.add(new ArrayList<Integer>(Collections.nCopies(numberOfResources, 0)));//allocates the space of max number of resources
        requestingResources.get(0).set(0, 1);// process one is requesting one instance of resource 0
        requestingResources.get(0).set(1, 0);// process one is requesting 0 instance of resource 1

        //process Two
        requestingResources.add(new ArrayList<Integer>(Collections.nCopies(numberOfResources, 0)));//allocates the space of max number of resources
        requestingResources.get(1).set(0, 1);// process two is requesting 1 instance of resource 0
        requestingResources.get(1).set(1, 0);// process two is requesting 0 instance of resource 1

        OperationalSystem os = new OperationalSystem(3000);
        boolean deadlock = os.checkDeadLock(existingResources, availableResources, allocatedResources, requestingResources).size() > 0;

        Assert.assertTrue("ERROR !!! This situation must generate deadlock",deadlock);
    }

}