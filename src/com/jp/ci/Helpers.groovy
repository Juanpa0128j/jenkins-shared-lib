package com.jp.ci

class Helpers implements Serializable {
    static String branchToTag(String branch, String version='v1.0') {
        return "${branch}-${version}"
    }
}
