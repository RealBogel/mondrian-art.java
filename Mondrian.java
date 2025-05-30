// Tristan Suwito
// 13 May 2025
// CSE 123
// TA: Rushil Arun & Chris Ma
// C2: Mondrian Art
// This class simulates Piet Mondrian's artwork by generating images using an algorithm that 
// subdivides a blank canvas into smaller regions and randomly colors each region. There is a
// choice between paintBasicMondrain, which gives a simple grid-like image, and 
// paintComplexMondrian, which uses a random number of splits to introduce a variety of 
// different shaped ractangles to the image. 
import java.util.*;
import java.awt.*;

public class Mondrian {
    private static final int MIN_SIZE = 300; // Minimum size for the canvas
    private static final int MIN_REGION_SIZE = 20; // Minimum size for a region to be split
    private static final Color[] COLORS = {Color.RED, Color.YELLOW, Color.CYAN, Color.WHITE};
    private Random rand = new Random();
        // Behavior:
        //   - This method preps the 'canvas' by turning it to black and divides the 'canvas' 
        //     into smaller rectangle regions using simple splits. Regions split below a certain
        //     size are randomly colored red, yellow, cyan, or white, and left with a one-pixel
        //     black border. 
        // Parameters:
        //   - pixels: 2D array of Color objects, representing a grid of pixels that act as the 
        //     'canvas' for the simulated Mondrian art.
        // Returns:
        // Exceptions:
        //   - Throws IllegalArgumentException:
        //     pixels is null
        //     pixels height is less than MIN_SIZE
        //     pixels width is less than MIN_SIZE
        public void paintBasicMondrian(Color[][] pixels) {
            if (pixels == null || pixels.length < MIN_SIZE || pixels[0].length < MIN_SIZE) {
                throw new IllegalArgumentException("Pixels cannot be null. Length & width cannot"
                                                     + " be less than 300.");
            }
            int width = pixels[0].length;
            int height = pixels.length;

            fillBlack(pixels, width, height);
            paintBasicMondrian(pixels, 0, width - 1, 0, height - 1, width, height);
        }

        // Behavior:
        //   - This method recursively divides a rectangular region of the 'canvas' into smaller
        //     subregions following these rules:
        //              - If the region is at least 1/4 of the full 'canvas', it is split into 4
        //                smaller regions. One vertical and horizontal dividng line chosen at
        //                random.
        //              - If the region is at least 1/4 the full 'canvas' height, it is split 
        //                horizontally into two regions at random.
        //              - If the region is at least 1/4 the full 'canvas' width, it is split
        //                vertically into two regions at random.
        //     Additionally, if the region is less than 1/4 of the full 'canvas', it is randomly
        //     colored red, yellow, cyan, or white, with a one-pixel black border through modifying
        //     the pixels array.
        // Parameters: 
        //   - pixels: 2D array of Color objects, representing a grid of pixels that act as the 
        //     'canvas' for the simulated Mondrian art. Allows 'canvas' to be modified.
        //   - x1: int representing the starting x-coordinate of the region.
        //   - x2: int representing the ending x-coordinate of the region.
        //   - y1: int representing the starting y-coordinate of the region.
        //   - y2: int representing the ending y-coordinate of the region.
        //   - width: int representing the full width of the 'canvas'.
        //   - height: int representing the full height of the 'canvas'.
        // Returns:
        // Exceptions:
        private void paintBasicMondrian(Color[][] pixels, int x1, int x2, int y1, int y2,
                                         int width, int height) {
            int regionWidth = x2 - x1 + 1;
            int regionHeight = y2 - y1 + 1;
            boolean splitVert = (regionWidth >= (width / 4));
            boolean splitHori = (regionHeight >= (height / 4));
            boolean shouldFill = true;

            if (splitVert && splitHori && regionWidth >= MIN_REGION_SIZE &&
                 regionHeight >= MIN_REGION_SIZE) {
                int splitX = rand.nextInt(regionWidth - MIN_REGION_SIZE) + x1 + 10;
                int splitY = rand.nextInt(regionHeight - MIN_REGION_SIZE) + y1 + 10;

                paintBasicMondrian(pixels, x1, splitX - 1, y1, splitY - 1, width, height);  // TL
                paintBasicMondrian(pixels, splitX, x2, y1, splitY - 1, width, height);  // TR
                paintBasicMondrian(pixels, x1, splitX - 1, splitY, y2, width, height);  // BL
                paintBasicMondrian(pixels, splitX, x2, splitY, y2, width, height);  // BR
                shouldFill = false;
            } else if (splitHori && regionHeight >= MIN_REGION_SIZE) {
                int splitY = rand.nextInt(regionHeight - MIN_REGION_SIZE) + y1 + 10;

                paintBasicMondrian(pixels, x1, x2, y1, splitY - 1, width, height);
                paintBasicMondrian(pixels, x1, x2, splitY, y2, width, height);
                shouldFill = false;
            } else if (splitVert && regionWidth >= MIN_REGION_SIZE) {
                int splitX = rand.nextInt(regionWidth - MIN_REGION_SIZE) + x1 + 10;

                paintBasicMondrian(pixels, x1, splitX - 1, y1, y2, width, height);
                paintBasicMondrian(pixels, splitX, x2, y1, y2, width, height);
                shouldFill = false;
            } 

            if (shouldFill) {
                fillWithBorder(pixels, x1, x2, y1, y2, COLORS[rand.nextInt(COLORS.length)]);
            }
        }

        // Behavior:
        //   - This method preps the 'canvas' by turning it to black and divides the 'canvas' 
        //     into smaller rectangle regions using erratic and complex splits. Regions split
        //     below a certain size are randomly colored red, yellow, cyan, or white, and left
        //     with a one-pixel black border. 
        // Parameters:
        //   - pixels: 2D array of Color objects, representing a grid of pixels that act as the 
        //     'canvas' for the simulated Mondrian art.
        // Returns:
        // Exceptions:
        //   - Throws IllegalArgumentException:
        //     pixels is null
        //     pixels height is less than MIN_SIZE
        //     pixels width is less than MIN_SIZE
        public void paintComplexMondrian(Color[][] pixels) {
            if (pixels == null || pixels.length < MIN_SIZE || pixels[0].length < MIN_SIZE) {
                throw new IllegalArgumentException("Pixels cannot be null. Length & width cannot" 
                                                    +" be less than 300.");
            }
            int width = pixels[0].length;
            int height = pixels.length;

            fillBlack(pixels, width, height);
            paintComplexMondrian(pixels, 0, width - 1, 0, height - 1, width, height);
        }

        // Behavior:
        //   - This method recursively divides a rectangular region of the 'canvas' into smaller
        //     subregions following these  rules:
        //              - If the region at least 1/4 of the full 'canvas', it may be split randomly
        //                into 2 - 4 regions both horizontally and vertically.
        //              - Split points are randomly chosen, and each resulting subregion must be at
        //                least 10x10 pixels in size.
        //     The varying/random number of vertical and horizontal splits allow a greater variety
        //     of layouts than paintBasicMondrian, resulting in ireegular grid patterns. 
        //     Additionally, if the region is less than 1/4 of the full 'canvas', it is randomly
        //     colored red, yellow, cyan, or white, with a one-pixel black border through modifying
        //     the pixels array.
        // Parameters: 
        //   - pixels: 2D array of Color objects, representing a grid of pixels that act as the 
        //     'canvas' for the simulated Mondrian art. Allows 'canvas' to be modified.
        //   - x1: int representing the starting x-coordinate of the region.
        //   - x2: int representing the ending x-coordinate of the region.
        //   - y1: int representing the starting y-coordinate of the region.
        //   - y2: int representing the ending y-coordinate of the region.
        //   - width: int representing the full width of the 'canvas'.
        //   - height: int representing the full height of the 'canvas'.
        // Returns:
        // Exceptions:
        private void paintComplexMondrian(Color[][] pixels, int x1, int x2, int y1, int y2,
                                         int width, int height) {
            int regionWidth = x2 - x1 + 1;
            int regionHeight = y2 - y1 + 1;
            boolean splitHori = (regionWidth >= (width / 4));
            boolean splitVert = (regionHeight >= (height / 4));

            boolean shouldFill = true;
            if (splitHori && splitVert && regionWidth >= MIN_REGION_SIZE &&
                 regionHeight >= MIN_REGION_SIZE) {
                int numVertSplits = rand.nextInt(3) + 2;
                int numHoriSplits = rand.nextInt(3) + 2;

                int[] xSplits = generateSplitPositions(x1, x2, numVertSplits);
                int[] ySplits = generateSplitPositions(y1, y2, numHoriSplits);
                 
                for (int i = 0; i < xSplits.length - 1; i++) {
                    for (int j = 0; j < ySplits.length - 1; j++) {
                        paintComplexMondrian(pixels, xSplits[i], xSplits[i + 1] - 1, ySplits[j],
                                             ySplits[j + 1] - 1, width, height);
                    }
                }
                shouldFill = false;
            } else if (splitVert && regionWidth > MIN_REGION_SIZE) {
                int splits = rand.nextInt(3) + 2;
                int[] xSplits = generateSplitPositions(x1, x2, splits);
                for (int i = 0; i < xSplits.length - 1; i++) {
                    paintComplexMondrian(pixels, xSplits[i], xSplits[i + 1], y1, y2, width,
                                         height);
                }
                shouldFill = false;
            } else if (splitHori && regionHeight > MIN_REGION_SIZE) {
                int splits = rand.nextInt(3) + 2;
                int[] ySplits = generateSplitPositions(y1, y2, splits);
                for (int i = 0; i < ySplits.length - 1; i++) {
                    paintComplexMondrian(pixels, x1, x2, ySplits[i], ySplits[i + 1], width,
                                         height);
                }
                shouldFill = false;
            }   

            if (shouldFill) {
                fillWithBorder(pixels, x1, x2, y1, y2, COLORS[rand.nextInt(COLORS.length)]);
            }
        }
        
        // Behavior:
        //   - This method generates an int array of split positions within a given start and end 
        //     range, dividing it into a specified number of splits. If possible, each gap between
        //     positions is at least 10 pixels in size. If the desired number of splits is too many
        //     for the size of the region, it automatically reduces the number of splits to fit
        //     within the bounds.
        // Parameters: 
        //   - start: int representing the starting index of the region.
        //   - end: int representing the ending index of the region.
        //   - splits: int representing the number of regions to divide the area into.
        // Returns:
        //   - An integer array containing the boundaries of the split regions.
        // Exceptions:
        private int[] generateSplitPositions(int start, int end, int splits) {
            int regionSize = (end - start) + 1;
            int minSize = 10;
            if (regionSize < splits * minSize) {
                splits = Math.max(2, regionSize / minSize);
            }
            int[] positions = new int[splits + 1];
            positions[0] = start;
            positions[splits] = (end + 1);
            
            int availableSpace = regionSize - (splits * minSize);
            int[] gaps = new int[splits - 1];
            int total = 0;
            for (int i = 0; i < gaps.length; i++) {
                gaps[i] = rand.nextInt(availableSpace + 1);
                total += gaps[i];
            }

            int pos = start + minSize;
            for (int i = 1; i < splits; i++) {
                int offset = (int)((double)gaps[i - 1] / total * availableSpace);
                positions[i] = pos + offset;
                pos = positions[i] + minSize;
            }
            return positions;
        }

        // Behavior:
        //   - This method fills a region of the pixels array with the given color, excluding the
        //     outermost border, creating a black border around each color region.
        // Parameters: 
        //   - pixels: 2D array of Color objects, representing a grid of pixels that act as the 
        //     'canvas' for the simulated Mondrian art. Allows colors to be changed.
        //   - x1: int representing the starting x-coordinate of the region.
        //   - x2: int representing the ending x-coordinate of the region.
        //   - y1: int representing the starting y-coordinate of the region.
        //   - y2: int representing the ending y-coordinate of the region.
        //   - color: Color representing the color used to fill the inside of the region.
        // Returns:
        // Exceptions:
        private void fillWithBorder(Color[][] pixels, int x1, int x2, int y1, int y2,
                                    Color color) {
            if (x2 - x1 < 2 || y2 - y1 < 2) return;
        
            for (int y = y1 + 1; y < y2; y++) {
                for (int x = x1 + 1; x < x2; x++) {
                    pixels[y][x] = color;
                }
            }   
        }

        // Behavior:
        //   - This method fills the entire canvas with the color black.
        // Parameters: 
        //   - pixels: 2D array of Color objects, representing a grid of pixels that act as the 
        //     'canvas' for the simulated Mondrian art. Allows colors to be changed.
        //   - width: int representing the full width of the 'canvas'.
        //   - height: int representing the full height of the 'canvas'.
        // Returns:
        // Exceptions:
        private void fillBlack(Color[][] pixels, int width, int height) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    pixels[i][j] = Color.BLACK;
                }
            }
        }
}