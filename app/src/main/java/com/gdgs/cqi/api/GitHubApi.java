package com.gdgs.cqi.api;

import com.gdgs.cqi.model.GitHub;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * XGitHub
 * com.hayukleung.xgithub
 * GitHubApi.java
 *
 * by hayukleung
 * at 2017-03-31 10:46
 */

public interface GitHubApi {

  public static final String url_current_user = "https://api.github.com/user";
  public static final String url_current_user_authorizations_html =
      "https://github.com/settings/connections/applications{/client_id}";
  public static final String url_authorizations = "https://api.github.com/authorizations";
  public static final String url_code_search =
      "https://api.github.com/search/code?q={query}{&page,per_page,sort,order}";
  public static final String url_commit_search =
      "https://api.github.com/search/commits?q={query}{&page,per_page,sort,order}";
  public static final String url_emails = "https://api.github.com/user/emails";
  public static final String url_emojis = "https://api.github.com/emojis";
  public static final String url_events = "https://api.github.com/events";
  public static final String url_feeds = "https://api.github.com/feeds";
  public static final String url_followers = "https://api.github.com/user/followers";
  public static final String url_following = "https://api.github.com/user/following{/target}";
  public static final String url_gists = "https://api.github.com/gists{/gist_id}";
  public static final String url_hub = "https://api.github.com/hub";
  public static final String url_issue_search =
      "https://api.github.com/search/issues?q={query}{&page,per_page,sort,order}";
  public static final String url_issues = "https://api.github.com/issues";
  public static final String url_keys = "https://api.github.com/user/keys";
  public static final String url_notifications = "https://api.github.com/notifications";
  public static final String url_organization_repositories =
      "https://api.github.com/orgs/{org}/repos{?type,page,per_page,sort}";
  public static final String url_organization = "https://api.github.com/orgs/{org}";
  public static final String url_public_gists = "https://api.github.com/gists/public";
  public static final String url_rate_limit = "https://api.github.com/rate_limit";
  public static final String url_repository = "https://api.github.com/repos/{owner}/{repo}";
  public static final String url_repository_search =
      "https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}";
  public static final String url_current_user_repositories =
      "https://api.github.com/user/repos{?type,page,per_page,sort}";
  public static final String url_starred = "https://api.github.com/user/starred{/owner}{/repo}";
  public static final String url_starred_gists = "https://api.github.com/gists/starred";
  public static final String url_team = "https://api.github.com/teams";
  public static final String url_user_organizations = "https://api.github.com/user/orgs";
  public static final String url_user_repositories =
      "https://api.github.com/users/{user}/repos{?type,page,per_page,sort}";
  public static final String url_user_search =
      "https://api.github.com/search/users?q={query}{&page,per_page,sort,order}";

  // public static final String URL_USER = "https://api.github.com/users/{user}";
  // 获取用户基本信息
  @GET("/users/{user}") Observable<GitHub> api(@Path("user") String user);
}
