curl -XDELETE  'http://localhost:9200/live/'
curl -XDELETE  'http://localhost:9200/preview/'
curl -XPUT 'http://localhost:9200/live/'
curl -XPUT 'http://localhost:9200/preview/'

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
            "hippo_availability": {
                "type": "string"
            },
            "hippo_paths": {
                "type": "string"
            },
            "hippostd_state": {
                "type": "string"
            },
            "hippostd_stateSummary": {
                "type": "string"
            },
            "hippostdpubwf_createdBy": {
                "type": "string"
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
                "type": "string"
            },
            "hippostdpubwf_publicationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippotranslation_id": {
                "type": "string"
            },
            "hippotranslation_locale": {
                "type": "string"
            },
            "introduction": {
                "type": "string"
            },
            "jcr_uuid": {
                "type": "string"
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

curl -XPUT 'http://localhost:9200/*/_mapping/NewsPage' -d '{
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
                                "type": "string"
                            },
                            "newWindow": {
                                "type": "boolean"
                            }
                        }
                    },
                    "image": {
                        "properties": {
                            "hippo_docbase": {
                                "type": "string"
                            }
                        }
                    },
                    "paragraphImage": {
                        "properties": {
                            "paragraphImageLink": {
                                "properties": {
                                    "hippo_docbase": {
                                        "type": "string"
                                    }
                                }
                            },
                            "position": {
                                "type": "string"
                            },
                            "staticdropdown": {
                                "type": "string"
                            }
                        }
                    },
                    "quoteParameters": {
                        "properties": {
                            "image": {
                                "properties": {
                                    "hippo_docbase": {
                                        "type": "string"
                                    }
                                }
                            },
                            "name": {
                                "type": "string"
                            },
                            "quoteText": {
                                "type": "string"
                            }
                        }
                    },
                    "shareParameters": {
                        "properties": {
                            "linkedin": {
                                "type": "boolean"
                            },
                            "twitter": {
                                "type": "boolean"
                            }
                        }
                    },
                    "title": {
                        "type": "string"
                    },
                    "titleIcon": {
                        "properties": {
                            "hippo_docbase": {
                                "type": "string"
                            }
                        }
                    },
                    "youtubePlayerParameters": {
                        "properties": {
                            "allowFullScreen": {
                                "type": "boolean"
                            },
                            "autoplay": {
                                "type": "boolean"
                            },
                            "disablekb": {
                                "type": "boolean"
                            },
                            "rel": {
                                "type": "boolean"
                            },
                            "showinfo": {
                                "type": "boolean"
                            }
                        }
                    },
                    "youtubeUrlParameters": {
                        "properties": {
                            "theme": {
                                "type": "string"
                            },
                            "youtubeUrl": {
                                "type": "string"
                            }
                        }
                    }
                }
            },
            "headerImage": {
                "properties": {
                    "hippo_docbase": {
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
            "hippo_availability": {
                "type": "string"
            },
            "hippo_paths": {
                "type": "string"
            },
            "hippostd_state": {
                "type": "string"
            },
            "hippostd_stateSummary": {
                "type": "string"
            },
            "hippostdpubwf_createdBy": {
                "type": "string"
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
                "type": "string"
            },
            "hippostdpubwf_publicationDate": {
                "type": "date",
                "format": "dateOptionalTime"
            },
            "hippotranslation_id": {
                "type": "string"
            },
            "hippotranslation_locale": {
                "type": "string"
            },
            "introduction": {
                "type": "string"
            },
            "jcr_uuid": {
                "type": "string"
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
                "type": "string"
            },
            "subtitle": {
                "type": "string"
            },
            "thematags": {
                "type": "string"
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