# spring-boot-3-sample

This sample application is to sample spring apps service discovery with [spring-cloud-kubernetes](https://github.com/spring-cloud/spring-cloud-kubernetes) in K8 env. For other envs you can use Eureka(which is actually the default).

## Deployment Steps

### Build and Dockerize

Build the whole project by executing the below command in the root :
````
mvn clean install
````

open a terminal in each project root and execute the command:
````
docker build -t <projectname> . 
````
replace the <projectname> placeholder with the correct project name. 

### Tag and Push the docker images

Execute the below commands for each sub project. 
````
docker tag <projectname> <dockerUsername>/microdev:<projectname>
````
change the project name and the docker username. Using microdev is also optional. 

NOTE: You will then need to update the deployment.yml file with the above tagged image names. 

E.g: If the api-gateway project is tagged as dockeruser95/microdev:api-gateway, then the image name in the deployment.yml file should be  updated to dockeruser95/microdev:api-gateway 

Push the tagged images 
````
docker push <dockerUsername>/microdev:<projectname>
````

### Deploy

To deploy, execute the below command in the project root. 
```` 
kubectl apply  -f deployment.yaml  
````
This should spawn the pods and services in your k8 environment. 

### Import Keycloak Realm. 

Using the command 
````
kubectl port-forward <keycloak-podname> 8080:8080
````
you can port forward the keycloak to localhost. 
Once you can access keycloak through `http://localhost:8080`, login to the admin console and got to create new realm.
Here you can import the `realm-export.json` file in this projects root to add the user data. 

### Access

You can only access the services through the api-gateway service which is opened to the outside environment through ``localhost:9099``
