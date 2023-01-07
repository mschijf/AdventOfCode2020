package com.adventofcode.old.december04;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PassPort {

    public static void main(String[] args) {
        PassPort pp = new PassPort();
        int output = pp.run();
        System.out.println("Puzzle output : " + output);

    }

    //-----------------------------------------------------------

    private Input input;
    private static final Set<String> allFields = new HashSet<String>(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid"));
    private static final Set<String> mandatoryFields = new HashSet<String>(Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"));

    public PassPort() {
//        input = new Input("input04_example");
        input = new Input("input04_1");
    }

    public int run() {
        int count = 0;
        for (HashMap<String, String> passport : input.getInputList()) {
            Set<String> setAnd = interSection(passport.keySet(), mandatoryFields);
            if (setAnd.size() == mandatoryFields.size()) {
                if (validFields(passport)) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean validFields(HashMap<String, String> passport) {
        return checkBYR(passport.get("byr")) &&
                checkIYR(passport.get("iyr")) &&
                checkEYR(passport.get("eyr")) &&
                checkHGT(passport.get("hgt")) &&
                checkHCL(passport.get("hcl")) &&
                checkECL(passport.get("ecl")) &&
                checkPID(passport.get("pid"));
    }

    private boolean isNumber(String s, int min, int max) {
        try {
            int i = Integer.valueOf(s);
            return (i >= min && i <= max);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkBYR(String s) {
        if (s.length() != 4)
            return false;

        return isNumber(s, 1920, 2002);
    }

    private boolean checkIYR(String s) {
        if (s.length() != 4)
            return false;

        return isNumber(s, 2010, 2020);
    }

    private boolean checkEYR(String s) {
        if (s.length() != 4)
            return false;

        return isNumber(s, 2020, 2030);
    }

    private boolean checkHGT(String s) {
        if (s.length() < 3)
            return false;
        if (s.endsWith("cm")) {
            return isNumber(s.substring(0, s.length() - 2), 150, 193);
        } else if (s.endsWith("in")) {
            return isNumber(s.substring(0, s.length() - 2), 59, 76);
        } else {
            return false;
        }
    }

    private boolean checkHCL(String s) {
        if (s.length() != 7)
            return false;
        if (s.charAt(0) != '#')
            return false;
        for (int i = 1; i < 7; ++i) {
            char ch = s.charAt(i);
            if (!((ch >= 'a' && ch <= 'f') || (ch >= '0' && ch <= '9')))
                return false;
        }
        return true;
    }

    private boolean checkECL(String s) {
        switch(s) {
            case "amb": return true;
            case "blu": return true;
            case "brn": return true;
            case "gry": return true;
            case "grn": return true;
            case "hzl": return true;
            case "oth": return true;
        }
        return false;
    }

    private boolean checkPID(String s) {
        if (s.length() != 9)
            return false;
        for (int i = 0; i < 9; ++i) {
            char ch = s.charAt(i);
            if (!(ch >= '0' && ch <= '9'))
                return false;
        }
        return true;
    }

    private <T> Set<T> interSection(Set<T> set1, Set<T> set2) {
        Set<T> result = new HashSet<T>();
        for (T item : set1) {
            for (T checkItem : set2) {
                if (item.equals(checkItem)) {
                    result.add(item);
                    break;
                }
            }
        }
        return result;
    }


}
