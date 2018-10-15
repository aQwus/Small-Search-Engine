# Small-Search-Engine
Implementation of a basic data structure underlying search engines: an **inverted index**. The search engine here uses inverted index to answer some simple search queries. Each webpage is considerd as a set of words and the words are listed according to their positions in the webpage. 
The question of how to measure the relevance of a webpage to a particular query is an involved question with no easy answers. However, a simple scoring function is used here which depends on the relevance, i.e. _term frequency_ and _inverse document frequency_ of each word in a webpage.
