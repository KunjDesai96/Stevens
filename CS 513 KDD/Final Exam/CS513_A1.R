###########################################################################################
# Company   : Stevens 
# Course    : Data Mining
# Purpose   : Answers 1.1 and 1.2
# First Name: Kunj
# Last Name : Desai 
# ID        : 1044511
# Date      : 05/13/2020
##########################################################################################

#Step 0 - Remove all objects
rm(list=ls())

#Step 0 - Choosing the data
admission_dataSet <- read.csv(file.choose(),na.strings = '?')

#Step 1 - Viewing and summarizing the data
View(admission_dataSet)
summary(admission_dataSet)
table(admission_dataSet$ADMIT)

# Step 2 - Factorizing ADMIT and labeling levels 0 = "NOT ADMIT" and level 1 = "ADMIT"
admission_dataSet$ADMIT <- factor(admission_dataSet$ADMIT, levels = c(0, 1), labels = c('Not-Admit', 'Admit'))
admission_dataSet<-admission_dataSet[-1]

# Step 3 - 1.1 Using the kmeans clustering method to create two clusters for the Admission dataset using gre and gpa as clustering variables.
kmeans_data<- kmeans(admission_dataSet[,c(2, 3)], 2, nstart = 10)
kmeans_data$cluster
table(kmeans_data$cluster,admission_dataSet$ADMIT)

# Step 4 - 1.2 Using the hierarchical clustering method to create two clusters for the Admission dataset using gre and gpa as clustering variables.
admission_dist<-dist(admission_dataSet[,c (2, 3)])
hclust_results<-hclust(admission_dist)
plot(hclust_results)
hclust_2<-cutree(hclust_results,2)
table(hclust_2,admission_dataSet$ADMIT)
