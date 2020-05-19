###########################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : 8.1 Using hclust, categorize the “wisc_bc_ContinuousVar.csv” data into two (2) clusters based on. All the features except the diagnosis column. Tabulate the clustered rows against the “diagnosis” column. (Remove the rows with missing values first)
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
?dist()
cancer_data<-na.omit(cancer_data)
cancer_data<-cancer_data[-1]
cancer_dist<-dist(cancer_data[,-1])
hclust_results<-hclust(cancer_dist)
plot(hclust_results)
hclust_2<-cutree(hclust_results,2)
table(hclust_2,cancer_data[,1])
