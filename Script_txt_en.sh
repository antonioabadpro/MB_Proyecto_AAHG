#!/bin/bash

# Nombre de la colección
COLLECTION="coleccionPrueba"

# URL base de Solr
SOLR_URL="http://localhost:8983/solr"

echo "=== Creando colección '${COLLECTION}' ==="
curl -s "${SOLR_URL}/admin/collections?action=CREATE&name=${COLLECTION}&numShards=1&replicationFactor=1&wt=json"
echo -e "\nColección creada (si no existía)."

echo "=== Añadiendo campo 'texto' de tipo 'text_en' ==="
curl -s -X POST -H 'Content-type:application/json' \
  "${SOLR_URL}/${COLLECTION}/schema" \
  --data-binary '{
    "add-field": {
      "name": "texto",
      "type": "text_en",
      "stored": true,
      "indexed": true
    }
  }'
echo -e "\nCampo 'texto' añadido correctamente."
