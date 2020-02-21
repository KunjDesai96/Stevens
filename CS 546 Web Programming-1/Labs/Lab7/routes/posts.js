const express = require("express");
const router = express.Router();
const postsData = require("../data/posts");


router.get("/:id", async (req, res) => {
  try {
    const post = await postsData.getPostById(req.params.id);
    res.status(200).json(post);
  } catch (e) {
    res.status(404).json({ message: "Post not found" });
  }
});

router.get("/", async (req, res) => {
  try {
    const postList = await postsData.getAllPosts();
    res.json(postList);
  } catch (e) {
    res.status(500).send();
  }
});

router.post("/", async (req, res) => {
  const blogpostsData = req.body;

  if (!blogpostsData) {
    res.status(400).json({error: 'You must provide valid data '});
    return;
  }

  if (!blogpostsData.title) {
    res.status(400).json({error: 'You must provide a title'});
    return;
  }

  if (!blogpostsData.author) {
    res.status(400).json({error: 'You must provide an author'});
    return;
  }

  if (!blogpostsData.content) {
    res.status(400).json({error: 'You must provide a content'});
    return;
  }

  try {
    const {title, author, content} = blogpostsData;
    const newPost = await postsData.addPost(title, author, content);
    res.status(200).json(newPost);
  } catch (e) {
    res.status(400).json({error: e});
  }
});

router.put('/:id', async (req, res) => {
    const updatedData = req.body;

    if(!updatedData)
    {
      res.status(400).json({ error: 'You must provide data to update a post' });
      return;
    }

    if(!updatedData.newTitle && !updatedData.newContent)
    {
      res.status(400).json({ error: 'You must provide either title or new content' });
      return;
    }
    try {
      await postsData.getPostById(req.params.id);
    } catch (e) {
      res.status(404).json({error: 'Post not found'});
      return;
    }
  
    try {
      const updatedPost = await postsData.updatePost(req.params.id, updatedData.newTitle, updatedData.newContent);
      res.json(updatedPost);
    } catch (e) {
      res.status(400).json({error: e});
    }
  });

router.delete('/:id', async (req, res) => {
    try {
      await postsData.getPostById(req.params.id);
    } catch (e) {
      res.status(404).json({error: 'Post not found'});
      return;
    }
    try {
      const deletedData = await postsData.removePost(req.params.id);
      res.json(deletedData);
    } catch (e) {
      res.status(500).json({error: e});
    }
  });
module.exports = router;