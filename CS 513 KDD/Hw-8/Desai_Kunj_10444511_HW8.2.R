###########################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : 8.2 Using k-means, categorize the “wisc_bc_ContinuousVar.csv” data into two (2) clusters based on. All the features except the diagnosis column. Tabulate the clustered rows against the “diagnosis” column. (Remove the rows with missing values first)
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 04/27/2020
##########################################################################################

## remove all objects
rm(list=ls())

cancer_data<-read.csv(file.choose(),na.strings = '?')
View(cancer_data)

#summary
summary(cancer_data)
table(cancer_data$diagnosis)

#To factor the data set
cancer_data<-na.omit(cancer_data)
cancer_data<-cancer_data[-1]
kmeans_data<- kmeans(cancer_data[,-1],2,nstart = 10)
kmeans_data$cluster
table(kmeans_data$cluster,cancer_data[,1])

?kmeans()
