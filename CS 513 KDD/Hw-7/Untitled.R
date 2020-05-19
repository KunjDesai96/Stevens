###########################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : Use the ANN methodology with five (5) nodes in the hidden layer, to develop a classification model for the STATUS.
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 04/26/2020
##########################################################################################

## remove all objects
rm(list=ls()) 
data <- read.csv(file.choose())

### remove all the records with missing value
levels(data$STATUS)

data <- subset(data, select = -c(1,4,7,10,11,12,14,16,22))
ann_data<-data.frame(lapply(data,as.numeric))

idx <- seq (1,nrow(ann_data),by=5)
test<- ann_data[idx,]
training<-ann_data[-idx,]

library("neuralnet")
# ?neuralnet()
class(training$STATUS)
net_ann<- neuralnet( STATUS~.,training, hidden=5, threshold=0.01)

#Plot the neural network
plot(net_ann)

## test should have only the input colum
prediction <- predict(net_ann,test)

ann_cat<-ifelse(prediction <1.5,1,2)
length(ann_cat)

table(Actual=test$STATUS,prediction=ann_cat)

wrong<- (test$STATUS!=ann_cat)
error_rate<-sum(wrong)/length(wrong)
accuracy = 1-error_rate
accuracy