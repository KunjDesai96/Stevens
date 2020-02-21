const mongoCollections = require('../config/mongoCollections');
const bands = mongoCollections.bands;
var ObjectId = require('mongodb').ObjectId;     

let exportedMethods = {
  async getAllBands() {
    //TODO: Return an array of all bands from the DB

    const bandCollection = await bands();

    const bandAll = await bandCollection.find({}).toArray();

    return bandAll;
  },
  async getBandById(id) {
    //TODO: Return a band from the DB based on the ID

    if (!id || typeof id !== 'string'|| !ObjectId.isValid(id)) throw "You must provide a valid string id to search for";
    var o_id = new ObjectId(id);
    const bandCollection = await bands();

    const bandOne = await bandCollection.findOne({_id:o_id});
    if(bandOne === null) throw "No band with that id";

    return bandOne;
  },
  async addBand(bandName, bandMembers, yearFormed, genres, recordLabel) {
    /*
      TODO:  Add a band.  Be sure to check all input for proper TYPE, make sure input is THERE and VALID etc..
      bandName = string (can't be empty, undefined, null, a string etc..) 
      bandMembers = array of objects that contain band members first name and last name (can't be empty, undefined, null etc.. MUST have at least one band member) 
       [ {
          firstName,
          lastName
        }]
      yearFormed = string year the band formed (can't be empty, undefined, null, must be greater than or equal to 1900 less than 2019) 
      genres = array with at LEAST one element (can't be empty, undefined, null etc..) 
      recordLabel = string of the name of their record label (can't be empty, undefined, null etc..) 

      ex object:

      {
        bandName: "Pink Floyd",
        bandMembers: [
          {firstName: "Roger", lastName: "Waters"},
          {firstName: "David", lastName: "Gilmour"},
          {firstName: "Nick", lastName: "Mason"},
          {firstName: "Richard", lastName: "Wright"},
          {firstName: "Syd", lastName: "Barrett"}
        ],
        yearFormed: 1965,
        genre: ["Progressive Rock", "Psychedelic rock", "Classic Rock"],
        recordLabel: "EMI"
      }

      The function will return the newly inserted band, throw an error if the document cannot be inserted. 
    */

    if(!bandName || typeof bandName !== 'string') throw "You must provide a valid string."
    if(!bandMembers || !Array.isArray(bandMembers)) throw "You must provide a valid array."
    if(!yearFormed || typeof yearFormed !== 'string') throw "You must provide a valid string."
    if(!genres || !Array.isArray(genres)) throw "You must provide a valid array."
    if(!recordLabel || typeof recordLabel !== 'string') throw "You must provide a valid string."
    

    const bandCollection = await bands();

    const bandObject  =
    {
      bandName: bandName,
      bandMembers: bandMembers,
      yearFormed: yearFormed,
      genre: genres,
      recordLabel: recordLabel
    }
    const newInsertInformation = await bandCollection.insertOne(bandObject);

    if(newInsertInformation.insertedCount == 0)
    { 
      throw "Cannot insert band";
    }
    return await this.getBandById(newInsertInformation.insertedId.toString());
  },
  async removeBand(id) {
    // TODO: Removes a band from the DB, return the list of all bands once band has been deleted (call getAllBands())
    // id is a string/object ID, it cannot be blank, cannot be null, cannot be undefined, must be present
    //If not found or not removed, throw an error.

    if (!id || typeof id !== 'string' || !ObjectId.isValid(id)) throw "You must provide a valid string id to remove for";

    var o_id = new ObjectId(id);
    const bandCollection = await bands();
    const deleteInfo = await bandCollection.findOneAndDelete({_id:o_id});
    if (deleteInfo.value === null) {
        throw `Could not delete with id of ${id}`;
      }
    return deleteInfo.value;
  },
  async searchBandByName(bandName) {
    /*  
      bandName = string, can't be blank, null, undefined, a string.. etc...
      TODO: You will search the band name for the name supplied.  You will return wildcard matches..
      for example:  searchBandByName("Pink") would return "Pink Floyd", "Pink" or any band that had pink in it's name
     
      You will need to use a RegEx for this.  like so:
      let regex = new RegExp([".*", bandName, ".*"].join(""), "i");
      and then in your find query use the regex.  {"bandName": regex}

       If there are no bands found with that member then throw an error.
    */

    if(!bandName || typeof bandName !== 'string') throw "You must provide a valid string name";
    const bandCollection = await bands();
    let regex = new RegExp([".*", bandName, ".*"].join(""), "i");
    const bandFound  = await bandCollection.find({'bandName':regex}).toArray();

    if(bandFound.length < 1 )
    {
      throw "No such band found";
    }

    return bandFound;

  },
  async searchBandMemberFullName(firstName, lastName) {
    /*   
      TODO: You will search bands by band members for the input supplied.
      This needs to be an exact match so YOU WILL NEED TO USE AN LOGICAL AND for this. .  
      
      You will return a list of bands where that person is a band member.
      for example:  Corey Taylor is the singer for Slipknot and for Stone Sour.  If both of those bands exist in your DB
      and Corey Taylor is supplied then both bands would be returned. 

      If there are no bands found with that member then throw an error.
    */
    if(!lastName || typeof lastName !== 'string') throw "You must provide a valid string name";
    if(!firstName || typeof firstName !== 'string') throw "You must provide a valid string name";
   
    const bandCollection = await bands();
    const bandFound = await bandCollection
      .find({
        $and: [{'bandMembers.firstName': firstName}, {'bandMembers.lastName': lastName}]
      })
      .toArray();

      if(bandFound.length < 1 )
      {
        throw "No such band found";
      }
      return bandFound;
  },
  async searchBandMember(name) {
    /*  
      TODO: You will search bands by band members for the input supplied.  You will return wildcard matches..
      YOU WILL NEED TO USE AN LOGICAL OR for this. 
      for example:  searchBandMember("David") would return the band objects Pink Floyd (David Gilmour is a member), Van Halen (David Lee Roth is a member) 
      or any band that had David in their first or last name.  supplying "dav" should also return
      You will need to use a RegEx for this.  like so:
      let regex = new RegExp([".*", name, ".*"].join(""), "i");
      and then in your find query use the regex.  {"bandName": regex}
      .find({  $or: [{ "firstName": regex },{ "lastName": regex } }] }).toArray();
      
      You will return a list of bands where that person is a band member.
      for example:  David would return the objects Pink Floyd and Van Halen (if those bands were in your DB)
    */
    if(!name || typeof name !== 'string') throw "You must provide a valid string name";
    const bandCollection = await bands();
    let regex = new RegExp([".*", name, ".*"].join(""), "i");

    const bandFound = await bandCollection
      .find({
       $or: [{ "bandMembers.firstName": regex },{ "bandMembers.lastName": regex }]
      })
      .toArray();

      if(bandFound.length < 1 )
      {
        throw "No such band found";
      }
      return bandFound;
      
  },
  async searchBandByGenre(genre) {
    /*  
      TODO: This will return an array of objects of bands where the genre passed in matches one of the genres
      YOU WILL NEED TO USE MONGO's $in for the query... 

      Throw an error if no bands found
    */

      if(!genre || !Array.isArray(genre)) throw "You must provide a valid array."
      const bandCollection = await bands();
      const bandFound  = await bandCollection.find({genre: {$in: genre}}).toArray();


      if(bandFound.length < 1 )
      {
        throw "No such band found";
      }
      return bandFound;
   
  },
  async searchBandByYear(year) {
    /*  
      TODO: This will return an array of objects of bands that were formed in the year supplied

      Throw an error if no bands found
    */

    if(!year || typeof year !== 'string') throw "You must provide valid year"
    if(year < 0 || year.toString().length !== 4) throw "You must provide valid year"

      const bandCollection = await bands();
     const bandFound  = await bandCollection.find({'yearFormed':year}).toArray();
      if(bandFound.length < 1 )
      {
        throw "No such band found";
      }
      return bandFound;

  },
  async searchBandFormedBefore(year) {
    /*  
      TODO: This will return an array of objects of bands that were formed before in the year supplied $lt in mongo

      Throw an error if no bands found
    */

    if(!year || typeof year !== 'string') throw "You must provide valid year"
    if(year < 0 || year.toString().length !== 4) throw "You must provide valid year"

    const bandCollection = await bands();
    const bandFound  = await bandCollection.find({'yearFormed':{$lt: year}}).toArray();
      if(bandFound.length < 1 )
      {
        throw "No such band found";
      }
      return bandFound;
  },
  async searchBandFormedOnOrBefore(year) {
    /*  
      TODO: This will return an array of objects of bands that were formed on or before in the year supplied $lte in mongo

      Throw an error if no bands found
    */

    if(!year || typeof year !== 'string') throw "You must provide valid year"
    if(year < 0 || year.toString().length !== 4) throw "You must provide valid year"

    const bandCollection = await bands();
    const bandFound  = await bandCollection.find({'yearFormed':{$lte: year}}).toArray();
      if(bandFound.length < 1 )
      {
        throw "No such band found";
      }
      return bandFound;
  },
  async searchBandFormedAfter(year) {
    /*  
      TODO: This will return an array of objects of bands that were formed After in the year supplied $gt in mongo

      Throw an error if no bands found
    */
    if(!year || typeof year !== 'string') throw "You must provide valid year"
    if(year < 0 || year.toString().length !== 4) throw "You must provide valid year"

    const bandCollection = await bands();
    const bandFound  = await bandCollection.find({'yearFormed':{$gt: year}}).toArray();
      if(bandFound.length < 1 )
      {
        throw "No such band found";
      }
      return bandFound;
  },
  async searchBandFormedOnOrAfter(year) {
    /*  
      TODO: This will return an array of objects of bands that were formed on or after in the year supplied $gte in mongo

      Throw an error if no bands found
    */

    if(!year || typeof year !== 'string') throw "You must provide valid year"
    if(year < 0 || year.toString().length !== 4) throw "You must provide valid year"

    const bandCollection = await bands();
    const bandFound  = await bandCollection.find({'yearFormed':{$gte: year}}).toArray();
      if(bandFound.length < 1 )
      {
        throw "No such band found";
      }
      return bandFound;
  },
  async addBandMember(bandId, firstName, lastName) {
    /*  
      TODO: This will add a new band member object to the bandMember array
      it will return the band with the newly added member.  DO NOT ALLOW duplicates! $addToSet in Mongo...

      Throw an error if the member cannot be added
    */

    if (!bandId || typeof bandId !== 'string'|| !ObjectId.isValid(bandId)) throw "You must provide a valid string id to search for";
    if(!lastName || typeof lastName !== 'string') throw "You must provide a valid string name";
    if(!firstName || typeof firstName !== 'string') throw "You must provide a valid string name";
   
    var o_id = new ObjectId(bandId);
    const bandCollection = await bands();

    const updateInfo =
    {
      bandMembers   :  {firstName, lastName}
    }
    const updated = await bandCollection.updateOne({_id: o_id}, {$addToSet: updateInfo});
    if(updated.modifiedCount ==  0)
    {
      throw "Cannot add member";
    }
    return await this.getBandById(bandId);
  },
  async removeBandMember(bandId, firstName, lastName) {
    /*  
      TODO: This will remove a band member object to the bandMember array
      it will return the band with the newly removed member.

      Throw an error if the member cannot be removed
    */

    if (!bandId || typeof bandId !== 'string'|| !ObjectId.isValid(bandId)) throw "You must provide a valid string id to search for";
    if(!lastName || typeof lastName !== 'string') throw "You must provide a valid string name";
    if(!firstName || typeof firstName !== 'string') throw "You must provide a valid string name";
   
    var o_id = new ObjectId(bandId);
    const bandCollection = await bands();

    const removeInfo =
    {
      bandMembers   :  {firstName, lastName}
    }
    const removed = await bandCollection.updateOne({_id: o_id}, {$pull: removeInfo});
    if(removed.modifiedCount ==  0)
    {
      throw "Cannot remove member";
    }
    return await this.getBandById(bandId);
 
  }
};

module.exports = exportedMethods;