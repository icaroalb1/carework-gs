az login 

az provider register --namespace Microsoft.ContainerRegistry

az group create \
  --name rg-gs-carework \
  --location brazilsouth

az postgres flexible-server create \
  --resource-group rg-gs-carework \
  --name carework \
  --location brazilsouth \
  --admin-user carework \
  --admin-password carework \
  --tier Burstable \
  --sku-name Standard_B1ms \
  --version 16 \
  --storage-size 32 \
  --public-access all

az acr create \
  --resource-group rg-gs-carework \
  --name carework \
  --sku Premium \
  --location brazilsouth




