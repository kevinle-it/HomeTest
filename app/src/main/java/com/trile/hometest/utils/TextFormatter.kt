package com.trile.hometest.utils

import kotlin.math.abs
import kotlin.math.ceil


/**
 *
 * @author lmtri
 * @since Aug 09, 2019 at 1:18 PM
 */

fun String.formatText(): String {
    if (this.indexOf(' ') == -1) {
        return this
    }
    var result: String
    var midPos = ceil(this.length / 2.0).toInt()
    if (this[midPos] == ' ') {
        result = this.substring(0, midPos) + "\n" + this.substring(midPos + 1)
    } else {
        // Find first space to the left from midPos
        var spaceToTheLeft = midPos
        while (spaceToTheLeft > 0 && this[spaceToTheLeft] != ' ') {
            --spaceToTheLeft
        }
        // Find first space to the right from midPos
        var spaceToTheRight = midPos
        while (spaceToTheRight < this.length - 1 && this[spaceToTheRight] != ' ') {
            ++spaceToTheRight
        }
        // If no space to the left from midPos
        if (spaceToTheLeft == 0) {
            result = this.substring(0, spaceToTheRight) + "\n" + this.substring(spaceToTheRight + 1)
        } else if (spaceToTheRight == 0) {  // If no space to right from midPos
            result = this.substring(0, spaceToTheLeft) + "\n" + this.substring(spaceToTheLeft + 1)
        } else {    // There are 2 nearest spaces to the left and right from midPos

            // spaceToTheLeft position == length of left-side substring by left-side space from midPos
            // (this.length - 1) - spaceToTheLeft == length of right-side substring by left-side space from midPos

            // spaceToTheRight position == length of left-side substring by right-side space from midPos
            // (this.length - 1) - spaceToTheRight == length of right-side substring by right-side space from midPos

            // We compare the difference between them to find the minimal one
            if (abs(spaceToTheLeft - ((this.length - 1) - spaceToTheLeft))
                < abs(spaceToTheRight - ((this.length - 1) - spaceToTheRight))
            ) {
                result = this.substring(0, spaceToTheLeft) + "\n" + this.substring(spaceToTheLeft + 1)
            } else {
                result = this.substring(0, spaceToTheRight) + "\n" + this.substring(spaceToTheRight + 1)
            }
        }
    }
    return result
}