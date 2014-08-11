curl -XDELETE  'http://localhost:9200/live/'
curl -XDELETE  'http://localhost:9200/preview/'
curl -XPUT 'http://localhost:9200/live/'
curl -XPUT 'http://localhost:9200/preview/'

# NewsPage

curl -XPUT 'http://localhost:9200/*/_mapping/NewsPage' -d '
{
    "NewsPage": {
        "properties": {
            "author": {
                "type": "string"
            },
            "browserTitle": {
                "type": "string"
            },
            "description": {
                "type": "string"
            },
            "flexibleblock": {
                "properties": {
                    "content": {
                        "properties": {
                            "hippostd_content": {
                                "type": "string"
                            }
                        }
                    },
                    "externallinks": {
                        "properties": {
                            "alt": {
                                "type": "string"
                            },
                            "linkTitle": {
                                "type": "string"
                            },
                            "linkUrl": {
                                "type": "string",
                				"index" : "not_analyzed"
                            },
                            "newWindow": {
                                "type": "boolean"
                            }
                        }
                    },
                    "quoteParameters": {
                        "properties": {
                            "name": {
                                "type": "string"
                            },
                            "quoteText": {
                                "type": "string"
                            }
                        }
                    },
                    "title": {
                        "type": "string"
                    }
                }
            },
            "hideFromSearch": {
                "type": "boolean"
            },
            "hideFromSitemap": {
                "type": "boolean"
            },
            "hippo_paths": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_createdBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_creationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModificationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModifiedBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_publicationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippotranslation_locale": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "introduction": {
                "type": "string"
            },
            "jcr_uuid": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "keywords": {
                "type": "string"
            },
            "releaseDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "share": {
                "type": "boolean"
            },
            "subjecttags": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "subtitle": {
                "type": "string"
            },
            "thematags": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "title": {
                "type": "string"
            },
            "type": {
                "type": "string"
            }
        }
    }
 }'   

# EventPage
 
curl -XPUT 'http://localhost:9200/*/_mapping/EventPage' -d '
{ 
    "EventPage": {
        "properties": {
            "browserTitle": {
                "type": "string"
            },
            "description": {
                "type": "string"
            },
            "eventDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "flexibleblock": {
                "properties": {
                    "content": {
                        "properties": {
                            "hippostd_content": {
                                "type": "string"
                            }
                        }
                    },
                    "title": {
                        "type": "string"
                    }
                }
            },
            "hideFromSearch": {
                "type": "boolean"
            },
            "hideFromSitemap": {
                "type": "boolean"
            },
            "hippo_paths": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_createdBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_creationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModificationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModifiedBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_publicationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippotranslation_locale": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "introduction": {
                "type": "string"
            },
            "jcr_uuid": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "keywords": {
                "type": "string"
            },
            "releaseDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "share": {
                "type": "boolean"
            },
            "subjecttags": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "subtitle": {
                "type": "string"
            },
            "thematags": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "title": {
                "type": "string"
            }
        }
    }
}'

# ArticlePage
    
curl -XPUT 'http://localhost:9200/*/_mapping/ArticlePage' -d '
{
    "ArticlePage": {
        "properties": {
            "author": {
                "type": "string"
            },
            "browserTitle": {
                "type": "string"
            },
            "description": {
                "type": "string"
            },
            "flexibleblock": {
                "properties": {
                    "content": {
                        "properties": {
                            "hippostd_content": {
                                "type": "string"
                            }
                        }
                    },
                    "quoteParameters": {
                        "properties": {
                            "name": {
                                "type": "string"
                            },
                            "quoteText": {
                                "type": "string"
                            }
                        }
                    },
                    "title": {
                        "type": "string"
                    }
                }
            },
            "hideFromSearch": {
                "type": "boolean"
            },
            "hideFromSitemap": {
                "type": "boolean"
            },
            "hippo_paths": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_createdBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_creationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModificationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModifiedBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_publicationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippotranslation_locale": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "introduction": {
                "type": "string"
            },
            "jcr_uuid": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "keywords": {
                "type": "string"
            },
            "releaseDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "share": {
                "type": "boolean"
            },
            "subjecttags": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "subtitle": {
                "type": "string"
            },
            "thematags": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "title": {
                "type": "string"
            }
        }
    }
}'

# WebPage

curl -XPUT 'http://localhost:9200/*/_mapping/WebPage' -d '
{
    "WebPage": {
        "properties": {
            "author": {
                "type": "string"
            },
            "browserTitle": {
                "type": "string"
            },
            "description": {
                "type": "string"
            },
            "hideFromSearch": {
                "type": "boolean"
            },
            "hideFromSitemap": {
                "type": "boolean"
            },
            "hippo_paths": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_createdBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_creationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModificationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModifiedBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_publicationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippotranslation_locale": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "introduction": {
                "type": "string"
            },
            "jcr_uuid": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "keywords": {
                "type": "string"
            },
            "releaseDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "share": {
                "type": "boolean"
            },
            "title": {
                "type": "string"
            }
        }
    }
}'

# OverviewPage

curl -XPUT 'http://localhost:9200/*/_mapping/OverviewPage' -d '
{
    "OverviewPage": {
        "properties": {
            "author": {
                "type": "string"
            },
            "browserTitle": {
                "type": "string"
            },
            "description": {
                "type": "string"
            },
            "hideFromSearch": {
                "type": "boolean"
            },
            "hideFromSitemap": {
                "type": "boolean"
            },
            "hippo_paths": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_createdBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_creationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModificationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModifiedBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_publicationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippotranslation_locale": {
                "type": "string",
                "index" : "not_analyzed"
                
            },
            "jcr_uuid": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "keywords": {
                "type": "string"
            },
            "releaseDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "share": {
                "type": "boolean"
            },
            "subjecttags": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "thematags": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "title": {
                "type": "string"
            }
        }
    }
}'

# FaqDocument

curl -XPUT 'http://localhost:9200/*/_mapping/FaqDocument' -d '
{
    "FaqDocument": {
        "properties": {
            "author": {
                "type": "string"
            },
            "flexibleblock": {
                "properties": {
                    "content": {
                        "properties": {
                            "hippostd_content": {
                                "type": "string"
                            }
                        }
                    }
                }
            },
            "hideFromSearch": {
                "type": "boolean"
            },
            "hideFromSitemap": {
                "type": "boolean"
            },
            "hippo_paths": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_createdBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_creationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModificationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippostdpubwf_lastModifiedBy": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "hippostdpubwf_publicationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippotranslation_locale": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "jcr_uuid": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "releaseDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "subjecttags": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "thematags": {
                "type": "string",
                "index" : "not_analyzed"
            },
            "title": {
                "type": "string"
            }
        }
    }
}'