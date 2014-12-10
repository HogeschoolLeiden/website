curl -XDELETE  "$1/hsleidennl/"
curl -XDELETE  "$1/hsleidennl-preview/"

curl -XPUT "$1/hsleidennl" -d @mapping.json \
        --header "Content-Type: application/json"
curl -XPUT "$1/hsleidennl-preview" -d @mapping.json \
        --header "Content-Type: application/json"
