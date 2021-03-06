/*
Copyright 2007-2009 Selenium committers

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package org.openqa.selenium.internal.seleniumemulation;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;

import com.thoughtworks.selenium.SeleniumException;

public class OpenWindow extends SeleneseCommand<Void> {
  private final URL baseUrl;
  private final GetEval opener;

  public OpenWindow(String baseUrl, GetEval opener) {
    try {
      this.baseUrl = new URL(baseUrl);
    } catch (MalformedURLException e) {
      throw new SeleniumException(e.getMessage(), e);
    }
    this.opener = opener;
  }

  @Override
  protected Void handleSeleneseCommand(final WebDriver driver, final String url,
      final String windowID) {
    try {
      final String urlToOpen = url.indexOf("://") == -1 ?
          new URL(baseUrl, url).toString() :
          url;

      String[] args = {String.format("window.open('%s', '%s'); null;", urlToOpen, windowID)};

      opener.apply(driver, args);
    } catch (MalformedURLException e) {
      throw new SeleniumException(e.getMessage(), e);
    }

    return null;
  }
}
