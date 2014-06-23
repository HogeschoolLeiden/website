Feature: Youtube

Scenario: Add a youtube block on a news page and See whether it shows up
	Given I create a "NewsPage"
	And add a flexible block item "youtube" with the value "https://www.youtube.com/watch?v=2uIugnAHWiY" as "Youtube url"
	When I publish the document
	Then a video appearance in the front-end. 
	  