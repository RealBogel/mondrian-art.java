#Mondrian Art Generator (Java)
This Java assignment simulates the artistic style of Piet Mondrian by recursively subdividing a digital canvas into regions and filling them with bold, primary colors. Two distinct approaches—Basic and Complex—allow for the generation of minimalist grid-like artwork or intricate, randomized layouts.

Objectives:
Practice writing recursive algorithms for image generation

Use 2D arrays and Java’s Color class to simulate pixel-based drawing

Implement structured randomness using the Random class

Enhance understanding of graphics partitioning and boundary handling

Develop clean and modular Java code with thorough exception handling

Breakdown:
Core Methods:
paintBasicMondrian(Color[][] pixels):

- Subdivides the canvas into a grid of rectangular regions using simple rules

- Applies solid colors to small regions with black borders

paintComplexMondrian(Color[][] pixels):

- Recursively divides the canvas using variable numbers of splits

- Produces irregular and varied region sizes for a more chaotic layout

Helper Methods:
fillBlack(...): Fills the entire canvas with black before region processing

fillWithBorder(...): Fills a region with color and adds a black border

generateSplitPositions(...): Calculates random but valid subdivision points

Example Use:
You can integrate this code with a graphical frontend or testing client that displays a Color[][] canvas on-screen. Example image files or GUI components may be used for display and interaction.

Tools & Libraries
Java AWT (java.awt.Color)

Java Util (java.util.Random)

⚠Input Requirements
Canvas must be at least 300x300 pixels

All pixels must be initialized and non-null

Features:
Randomized recursive image generation

Basic vs. Complex mode for artistic variation

Handles edge cases and enforces minimum region size

