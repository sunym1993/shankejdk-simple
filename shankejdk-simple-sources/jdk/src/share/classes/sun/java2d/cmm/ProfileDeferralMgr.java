/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
 */

package sun.java2d.cmm;

import java.awt.color.ProfileDataException;
import java.util.Vector;


/**
 * A class to manage the deferral of CMM initialization of profile
 * data for internal ICC_Profile objects - i.e. when we "trust" that
 * the profile data is valid and we think it may not be needed.  An
 * example is the sRGB profile which gets loaded by any program doing
 * graphics, but which may not be needed if the program does not need
 * high quality color conversion.
 */
public class ProfileDeferralMgr {

    public static boolean deferring = true;
    private static Vector<ProfileActivator> aVector;

    /**
     * Records a ProfileActivator object whose activate method will
     * be called if the CMM needs to be activated.
     */
    public static void registerDeferral(ProfileActivator pa) {

        if (!deferring) {
            return;
        }
        if (aVector == null) {
            aVector = new Vector<ProfileActivator>(3, 3);
        }
        aVector.addElement(pa);
        return;
    }


    /**
     * Removes a ProfileActivator object from the vector of ProfileActivator
     * objects whose activate method will be called if the CMM needs to be
     * activated.
     */
    public static void unregisterDeferral(ProfileActivator pa) {

        if (!deferring) {
            return;
        }
        if (aVector == null) {
            return;
        }
        aVector.removeElement(pa);
        return;
    }

    /**
     * Removes a ProfileActivator object from the vector of ProfileActivator
     * objects whose activate method will be called if the CMM needs to be
     * activated.
     */
    public static void activateProfiles() {

        int i, n;

        deferring = false;
        if (aVector == null) {
            return;
        }
        n = aVector.size();
        for (ProfileActivator pa : aVector) {
            try {
                pa.activate();
            } catch (ProfileDataException e) {
                /*
                 * Ignore profile activation error for now:
                 * such exception is pssible due to absence
                 * or corruption of standard color profile.
                 * As for now we expect all profiles should
                 * be shiped with jre and presence of this
                 * exception is indication of some configuration
                 * problem in jre installation.
                 *
                 * NB: we still are greedy loading deferred profiles
                 * and load them all if any of them is needed.
                 * Therefore broken profile (if any) might be never used.
                 * If there will be attempt to use broken profile then
                 * it will result in CMMException.
                 */
            }
        }
        aVector.removeAllElements();
        aVector = null;
        return;
    }

}
