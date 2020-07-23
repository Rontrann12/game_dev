<h1> Notes while developing</h1>


<h3> LibGDX</h3>

<h4> Drawing images </h3>

When an image file is loaded into the GPU, this is called a texture. You can define shapes (commonly rectables) 
to define a TextureRegion(A subset of the texture) which will be drawn on screen.

It is very inefficient to have to define rectables everytime a new texture is loaded

<h5>SpriteBatch</h5>
It is more common to have one texture containing multiple sprites and defining a batch of
rectangles to draw individual sprites. This is handled by SpriteBatch class:


<h5>Texture</h5>

The texture class in libGDX decodes an image file and creates a texture.
**The image dimensions should be powers of two for performance reasons**

To draw a texture, it must be drawn by the batch class or any implementation of the batch class.

<h5> TextureRegion </h5> 

The class describes a rectable inside of the texture for drawing. Useful for drawing
a portion of the texture. 

The same can be done by adding additional parameters to SpriteBatch, but 
TextureRegion class is more convenient (not convinced. How is it easier than passing params to spritebatch. Ans: all region info is contained in the TextureRegion object before being passed into batch)

<h5> Sprite </h5>

The sprite class describes both the texture region and the geometry where it will be drawn.
Disadvantage is that it couples model, with the region where it is drawn


<h4>UI</h4>

libGDX scene2d Ui can simplify UI design and development.
Buttons are included.
UI layout can be define easily.

**Will need to study how to use these**