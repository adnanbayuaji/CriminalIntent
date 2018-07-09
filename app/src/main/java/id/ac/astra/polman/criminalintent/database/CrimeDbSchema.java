package id.ac.astra.polman.criminalintent.database;

/**
 * Created by Jihad044 on 27/03/2018.
 */

public class CrimeDbSchema {
    public static final class CrimeTable{
        public static final String NAME = "crimes";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
            public static final String SUSPECTNUM = "suspectnum";
        }

    }
}
