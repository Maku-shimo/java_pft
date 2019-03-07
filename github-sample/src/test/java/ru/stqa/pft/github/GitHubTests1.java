package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GitHubTests1 {

    @Test
    public void testCommits() throws IOException {

        Github github = new RtGithub("");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("Maku-shimo", "java_pft")).commits();

        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }

    }
}
