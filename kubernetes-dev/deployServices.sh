#Deploy microservices
ACTUAL_TIME=$(date +%s)

sed -i "s/TIME-UPDATE-BUILD-FNG/${ACTUAL_TIME}/g" microservices/server/*.yml

sed -i "s/TIME-UPDATE-BUILD-FNG/${ACTUAL_TIME}/g" microservices/support/*.yml

sed -i "s/TIME-UPDATE-BUILD-FNG/${ACTUAL_TIME}/g" microservices/web/*.yml

kubectl apply -f microservices/server
kubectl apply -f microservices/support
kubectl apply -f microservices/web
kubectl apply -f config/ingress.yml

