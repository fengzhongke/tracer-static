package com.ali.trace.statics.util;

/**
 * @auther hanlang
 * @date 2019-11-05 15:54
 */
public class MatchUtils {

    public static boolean matching(String string, String wildcard) {
        return null != wildcard && null != string && matching(string, wildcard, 0, 0);
    }

    private static boolean matching(String string, String wildcard, int stringStartNdx, int patternStartNdx) {
        int pNdx = patternStartNdx;
        int sNdx = stringStartNdx;
        int pLen = wildcard.length();
        if (pLen == 1 && wildcard.charAt(0) == '*') {
            return true;
        } else {
            int sLen = string.length();
            boolean nextIsNotWildcard = false;

            while(sNdx < sLen) {
                if (pNdx >= pLen) {
                    return false;
                }

                char p = wildcard.charAt(pNdx);
                if (!nextIsNotWildcard) {
                    if (p == '\\') {
                        ++pNdx;
                        nextIsNotWildcard = true;
                        continue;
                    }

                    if (p == '?') {
                        ++sNdx;
                        ++pNdx;
                        continue;
                    }

                    if (p == '*') {
                        char pnext = 0;
                        if (pNdx + 1 < pLen) {
                            pnext = wildcard.charAt(pNdx + 1);
                        }

                        if (pnext != '*') {
                            ++pNdx;

                            for(int i = string.length(); i >= sNdx; --i) {
                                if (matching(string, wildcard, i, pNdx)) {
                                    return true;
                                }
                            }

                            return false;
                        }

                        ++pNdx;
                        continue;
                    }
                } else {
                    nextIsNotWildcard = false;
                }

                if (p != string.charAt(sNdx)) {
                    return false;
                }

                ++sNdx;
                ++pNdx;
            }

            while(pNdx < pLen && wildcard.charAt(pNdx) == '*') {
                ++pNdx;
            }

            return pNdx >= pLen;
        }
    }
}
