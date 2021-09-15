package com.mikael.rmcosmetics.objects

class HatAnimated(
    var display: String = "§aChapéu Animado",
    var lore: List<String> = listOf(""),
    var rarity: String = "comum",

    var urls: MutableMap<String, Int> = mutableMapOf<String, Int>(),
    var permission: String = "rmcosmetics.animatedhat.hat",
    var groupPermission: String = "rmcosmetics.animatedhat.vip",
    var exclusiveGroupName: String = "§fExclusivo para §7Membro §fou superior.",
) {
    var increasing = true
    var currentUrl = 0

    fun next() {
        if (increasing) {
            currentUrl++
            if (currentUrl == urls.size) {
                currentUrl--
                increasing = false
            }
        } else {
            currentUrl--
            if (currentUrl == 0) {
                increasing = true
            }
        }

        lastChange = System.currentTimeMillis()
    }

    var lastChange = System.currentTimeMillis()

    fun canChange(): Boolean {
        return System.currentTimeMillis() >
                (lastChange + (getCurrentDuration() * 50))
    }

    fun getCurrentUrl(): String = urls.keys.toList()[currentUrl]
    fun getCurrentDuration(): Int = urls.values.toList()[currentUrl]
}