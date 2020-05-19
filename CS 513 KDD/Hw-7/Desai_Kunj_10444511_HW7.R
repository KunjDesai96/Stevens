###########################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : Use the ANN methodology with five (5) nodes in the hidden layer, to develop a classification model for the Diagnosis.
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 04/26/2020
##########################################################################################

## remove all objects
rm(list=ls()) 
data <- read.csv(file.choose())

### remove all the records with missing value
levels(data$diagnosis)
## 1 = B, 2 = M
ann_data<-data.frame(lapply(na.omit(data[,-1]),as.numeric))

idx <- seq (1,nrow(ann_data),by=5)
test<- ann_data[idx,]
training<-ann_data[-idx,]

library("neuralnet")
# ?neuralnet()
class(training$diagnosis)
net_ann<- neuralnet( diagnosis~.,training, hidden=5, threshold=0.01)

#Plot the neural network
plot(net_ann)

## test should have only the input colum
prediction <- predict(net_ann,test)

ann_cat<-ifelse(prediction <1.5,1,2)
length(ann_cat)

table(Actual=test$diagnosis,prediction=ann_cat)

wrong<- (test$diagnosis!=ann_cat)
error_rate<-sum(wrong)/length(wrong)
error_rate