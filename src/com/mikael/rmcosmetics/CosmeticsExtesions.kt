package com.mikael.rmcosmetics

fun Double.percentColor(): String {
    if (this >= 1.0) {
        return "§b"
    }
    if (this >= 0.80) {
        return "§6"
    }
    if (this >= 0.65) {
        return "§a"
    }
    if (this >= 0.35) {
        return "§e"
    }
    if (this >= 0.01) {
        return "§7"
    }
    if (this == 0.0) {
        return "§c"
    }
    return "§c";
}