package main.JiraModel;

import main.DbModel.version;

import java.util.List;

public class VersionList {
    private List<version> versions;

    public List<version> getVersions() {
        return versions;
    }

    public void setVersions(List<version> versions) {
        this.versions = versions;
    }
}
