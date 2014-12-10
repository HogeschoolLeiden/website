curl -XDELETE  'http://localhost:9200/newlive/'
curl -XDELETE  'http://localhost:9200/newpreview/'



curl -XPUT "http://localhost:9200/newlive" -d @mapping.json \
		--header "Content-Type: application/json"
curl -XPUT "http://localhost:9200/newpreview" -d @mapping.json \
		--header "Content-Type: application/json"


