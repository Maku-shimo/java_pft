package ru.stqa.pft.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GitHubTests {

    @Test
    public void testCommits() throws IOException {

        Github github = new RtGithub("f2305e1418c8acf8f542f4bcafb9af28babcfb07");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("Maku-shimo", "java_pft")).commits();

        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }

    }
}
