const dataInfo = require("./dataInfo")

const getById = async function getById(id) {

    if (id === null || id === undefined) return Promise.reject('You must provide id!');
    if (typeof(id) !== 'number' || isNaN(id) || id < 0) return Promise.reject('Invalid value for id!');

    const peopleData = dataInfo.dataInfo;
    let personData = null;

    for (var i = 0; i < peopleData.length; i++) {
        if (peopleData[i].id === id) {
            personData = peopleData[i];
            break;
        }
    }
    // peopleData.forEach(element => {
    //     if (element.id === id) {
    //         personData = element;
    //         exit;
    //     }
    // });


    return new Promise((resolve, reject) => {
        setTimeout(() => {
            // find project
            if (personData) {
                resolve(personData);
            } else {
                reject(new Error("Data for given id not found."));
            }
        }, 5000);
    });
}

module.exports = {
    getById
}