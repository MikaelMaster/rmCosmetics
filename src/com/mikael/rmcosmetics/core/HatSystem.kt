package com.mikael.rmcosmetics.core

import com.mikael.rmcosmetics.objects.Hat
import com.mikael.rmcosmetics.objects.HatData
import net.eduard.redemikael.core.api.miftCore
import net.eduard.redemikael.core.objects.MiftProfile
import net.eduard.redemikael.core.user
import org.bukkit.entity.Player

object HatSystem {

    var usingHat = mutableMapOf<Player, Hat>()
    var hats = mutableListOf<Hat>()
    var hatsSelected = mutableMapOf<MiftProfile, HatData>()

    fun select(player: Player, hat: Hat) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.hat = hat.display
        usingHat[player] = hat
        selected.updateQueue()
    }

    fun hasSelected(player: Player): Boolean {
        return usingHat.containsKey(player)
    }

    fun getSelectedHat(player: Player): Hat {
        return usingHat[player]!!
    }

    fun deselect(player: Player) {
        val user = player.user
        val selected = getOrCreate(user)
        selected.hat = null
        usingHat.remove(player)
        selected.updateQueue()
    }


    fun load(player: Player) {
        val profile = player.user
        val hatSelected = miftCore.sqlManager.getDataOf<HatData>(profile) ?: return
        hatsSelected[profile] = hatSelected
        val hat = hats.firstOrNull { it.display == hatSelected.hat } ?: return
        usingHat[player] = hat
    }

    fun getOrCreate(profile: MiftProfile): HatData {
        if (hatsSelected.containsKey(profile)) {
            return hatsSelected[profile]!!
        }
        val hatSelected = HatData()
        hatSelected.player = profile
        hatSelected.insert()
        hatsSelected[profile] = hatSelected
        return hatSelected
    }


    init {
        val chapeu1 = Hat(
            "Minhoca",
            listOf(""),
            "http://textures.minecraft.net/texture/a791a3f1e9e0a39915a765394b4fa849acb3e6a9c2cfa88a5a9cdca6a368e7fe",
            "rmcosmetics.hat.minhoca"
        )
        val chapeu2 = Hat(
            "Guardião",
            listOf(""),
            "http://textures.minecraft.net/texture/c25af966a326f9d98466a7bf8582ca4da6453de271b3bc9e59f57a99b63511c6",
            "rmcosmetics.hat.guardiao"
        )
        val chapeu3 = Hat(
            "Dragão Negro",
            listOf(""),
            "http://textures.minecraft.net/texture/73480592266dd7f53681efeee3188af531eea53da4af583a67617deeb4f473",
            "rmcosmetics.hat.dragao_negro"
        )
        val chapeu4 = Hat(
            "Dragão de Fogo",
            listOf(""),
            "http://textures.minecraft.net/texture/a6747f0f5a7a27c5f10e99b158d7311d283372e1b7e6bfec51c8629ee97f8d",
            "rmcosmetics.hat.dragao_de_fogo"
        )
        val chapeu5 = Hat(
            "Fantasma",
            listOf(""),
            "http://textures.minecraft.net/texture/68d2183640218ab330ac56d2aab7e29a9790a545f691619e38578ea4a69ae0b6",
            "rmcosmetics.hat.fantasma"
        )
        val chapeu6 = Hat(
            "Wither",
            listOf(""),
            "http://textures.minecraft.net/texture/cdf74e323ed41436965f5c57ddf2815d5332fe999e68fbb9d6cf5c8bd4139f",
            "rmcosmetics.hat.wither"
        )
        val chapeu7 = Hat(
            "Abelha",
            listOf(""),
            "http://textures.minecraft.net/texture/fa464fee2f9e5d30b833476c30e11a15cc4855402fbea83bd5717d3a277b4fdc",
            "rmcosmetics.hat.abelha"
        )
        val chapeu8 = Hat(
            "Tubarão",
            listOf(""),
            "http://textures.minecraft.net/texture/20a5bb07164739a2fc64e21b629c999fd05a31399909851084bea2a0c7fc24bd",
            "rmcosmetics.hat.tubarao"
        )
        val chapeu9 = Hat(
            "Raposa Híbrida",
            listOf(""),
            "http://textures.minecraft.net/texture/16cc559cfcd6bd99f7270307b2f117d9902070f7f17b9c613226a28b89306d15",
            "rmcosmetics.hat.raposa_hibrida"
        )
        val chapeu10 = Hat(
            "Lobo",
            listOf(""),
            "http://textures.minecraft.net/texture/69d1d3113ec43ac2961dd59f28175fb4718873c6c448dfca8722317d67",
            "rmcosmetics.hat.lobo"
        )
        val chapeu11 = Hat(
            "Ovelha no Baú",
            listOf(""),
            "http://textures.minecraft.net/texture/a2a0f3696fd8a9f08363d4321f9b4de3cf18a329ac96190baa9bf7ac6bd2122b",
            "rmcosmetics.hat.ovelha_no_bau"
        )
        val chapeu12 = Hat(
            "Cubo Misterioso",
            listOf(""),
            "http://textures.minecraft.net/texture/76dd77274dd5d4dac038d6eb7a6d9aac36a19ce7dd5341964022afb5b6cfda1e",
            "rmcosmetics.hat.cubo_misterioso"
        )
        val chapeu13 = Hat(
            "Aquário",
            listOf(""),
            "http://textures.minecraft.net/texture/d8cda1c54cc8407aa430d894120a2bfb30dc0a7a671148a84c9456a647661236",
            "rmcosmetics.hat.aquario"
        )
        val chapeu14 = Hat(
            "Doge",
            listOf(""),
            "http://textures.minecraft.net/texture/385f40c7ca46355647419c264ce454eaa32e0809c19a6c99ee2aa074c1ba1297",
            "rmcosmetics.hat.doge"
        )
        val chapeu15 = Hat(
            "Mário",
            listOf(""),
            "http://textures.minecraft.net/texture/33906a8e09b4b8eae045ad70b83e1273bc1e717d1349f7fc99992a2271c91711",
            "rmcosmetics.hat.mario"
        )
        val chapeu16 = Hat(
            "Pipoca",
            listOf(""),
            "http://textures.minecraft.net/texture/1497b147cfae52205597f72e3c4ef52512e9677020e4b4fa7512c3c6acdd8c1",
            "rmcosmetics.hat.pipoca"
        )
        val chapeu17 = Hat(
            "Planeta Terra",
            listOf(""),
            "http://textures.minecraft.net/texture/2e2cc42015e6678f8fd49ccc01fbf787f1ba2c32bcf559a015332fc5db50",
            "rmcosmetics.hat.planeta_terra"
        )
        val chapeu18 = Hat(
            "Pokebola",
            listOf(""),
            "http://textures.minecraft.net/texture/d43d4b7ac24a1d650ddf73bd140f49fc12d2736fc14a8dc25c0f3f29d85f8f",
            "rmcosmetics.hat.pokebola"
        )
        val chapeu19 = Hat(
            "Buzz Lightyear",
            listOf(""),
            "http://textures.minecraft.net/texture/ce64706776e84fa8c90cfb95777759c1d17687730ea500ccbe99318b19549760",
            "rmcosmetics.hat.buzz_lightyear"
        )
        val chapeu20 = Hat(
            "Rato",
            listOf(""),
            "http://textures.minecraft.net/texture/a8abb471db0ab78703011979dc8b40798a941f3a4dec3ec61cbeec2af8cffe8",
            "rmcosmetics.hat.rato"
        )
        val chapeu21 = Hat(
            "???",
            listOf(""),
            "http://textures.minecraft.net/texture/c7b187e38b407feabaf190879d98a67f4c7052f3201f72e44571f537ea89d4c7",
            "rmcosmetics.hat.???"
        )
        val chapeu22 = Hat(
            "Elmo de Ouro",
            listOf(""),
            "http://textures.minecraft.net/texture/83f69ef27f3120a51050e6be66a0e3f8e89af888d4806716bf86f590da638317",
            "rmcosmetics.hat.elmo_de_ouro"
        )
        val chapeu23 = Hat(
            "Capacete de Astronauta",
            listOf(""),
            "http://textures.minecraft.net/texture/e4f53f3d996eb6303abc98b42f4af0e7f9459c80a886d1c6aac25e8ab033f376",
            "rmcosmetics.hat.capacete_de_astronauta"
        )
        val chapeu24 = Hat(
            "Switch",
            listOf(""),
            "http://textures.minecraft.net/texture/be7941a84c16709f5e4edb61926fbe820ac4310db02591a0102194304a6c59d1",
            "rmcosmetics.hat.switch"
        )
        val chapeu25 = Hat(
            "Peter Pan",
            listOf(""),
            "http://textures.minecraft.net/texture/761b4a07f9879002823e3e3e5db4be842716a2d7d77806a28a2852408120cd5f",
            "rmcosmetics.hat.peter_pan"
        )
        val chapeu26 = Hat(
            "Terra Grossa",
            listOf(""),
            "http://textures.minecraft.net/texture/d1b1aa03a1c1bdc7725d8f3a2f4274ac1dd188193e3200627a7f1ffe04a5c5b",
            "rmcosmetics.hat.terra_grossa"
        )
        val chapeu27 = Hat(
            "Cascalho",
            listOf(""),
            "http://textures.minecraft.net/texture/21c5840736229db9d9645bf9b409e73e706e3dc4fc30d78eb2079d20d929db9e",
            "rmcosmetics.hat.cascalho"
        )
        val chapeu28 = Hat(
            "Caveira de Ouro",
            listOf(""),
            "http://textures.minecraft.net/texture/f7883ea4809af745c97ca77e704485498905198d6ae28138adbd4c5d4b81b944",
            "rmcosmetics.hat.caveira_de_ouro"
        )
        val chapeu29 = Hat(
            "Pelúcia de Múmia",
            listOf(""),
            "http://textures.minecraft.net/texture/56a5f8a25d30dc9510d277e2f34d29a0524194cff732c845eef8fc5f1a53bff4",
            "rmcosmetics.hat.pelucia_de_mumia"
        )
        val chapeu30 = Hat(
            "Cubo Mágico",
            listOf(""),
            "http://textures.minecraft.net/texture/5b1ef2a4829a11fd903b5e31088662a8c56e471bb48643c0d9f95006d1820210",
            "rmcosmeitcs.hat.cubo_magico"
        )
        val chapeu31 = Hat(
            "Ovo de Dragão",
            listOf(""),
            "http://textures.minecraft.net/texture/e6ba9987f738e6d75d3b02c30d1480a360593ddb464bd1c81abb9d71d9e656c0",
            "rmcosmetics.hat.ovo_de_dragao"
        )
        val chapeu32 = Hat(
            "Optifine",
            listOf(""),
            "http://textures.minecraft.net/texture/48e172dcf56bb84eac9c1c9a55bc208301542d65784b3bb4392e7c5b076ce9c",
            "rmcosmetics.hat.optifine"
        )
        val chapeu33 = Hat(
            "Bandeira da Argentina",
            listOf(""),
            "http://textures.minecraft.net/texture/ed1dddc665614c9f6487ba9c666da7579561589a494ef744aaf8f4f88a16",
            "rmcosmetics.hat.bandeira_da_argentina"
        )
        val chapeu34 = Hat(
            "Bandeira do Brasil",
            listOf(""),
            "http://textures.minecraft.net/texture/9668a1fb6af81b231bbcc4de5f7f95803bbd194f5827da027fa70321cf47c",
            "rmcosmetics.hat.bandeira_do_brasil"
        )
        val chapeu35 = Hat(
            "Bandeira da Russia",
            listOf(""),
            "http://textures.minecraft.net/texture/16eafef980d6117dabe8982ac4b4509887e2c4621f6a8fe5c9b735a83d775ad",
            "rmcosmetics.hat.bandeira_da_russia"
        )
        val chapeu36 = Hat(
            "Bandeira do Japão",
            listOf(""),
            "http://textures.minecraft.net/texture/8043ae9bbfa8b8bbb5c964bbce45fbe79a3ad742be07b56607c68c8e11164",
            "rmcosmetics.hat.bandeira_do_japao"
        )
        val chapeu37 = Hat(
            "Bandeira da Chile",
            listOf(""),
            "http://textures.minecraft.net/texture/ed1dddc665614c9f6487ba9c666da7579561589a494ef744aaf8f4f88a16",
            "rmcosmetics.hat.bandeira_da_chile"
        )
        val chapeu38 = Hat(
            "Bandeira do Mexico",
            listOf(""),
            "http://textures.minecraft.net/texture/ca971bfcf33b439821b4e5f0c86cd61435cd3e0c819e35dd9774ef0399f132b",
            "rmcosmetics.hat.bandeira_do_mexico"
        )
        val chapeu39 = Hat(
            "Bandeira do Uruguai",
            listOf(""),
            "http://textures.minecraft.net/texture/28440597c4bc2aad600a54604dc7b1fb771343e022e6a2e022f90e40cc25f9f8",
            "rmcosmetics.hat.bandeira_do_uruguai"
        )
        val chapeu40 = Hat(
            "Bandeira da China",
            listOf(""),
            "http://textures.minecraft.net/texture/7f9bc035cdc80f1ab5e1198f29f3ad3fdd2b42d9a69aeb64de990681800b98dc",
            "rmcosmetics.hat.bandeira_da_china"
        )
        val chapeu41 = Hat(
            "Bandeira da Canadá",
            listOf(""),
            "http://textures.minecraft.net/texture/f241a697f6dfb1c57cda327baa6732a7828c398be4ebfdbd166c232bcae2b",
            "rmcosmetics.hat.bandeira_do_canada"
        )
        val chapeu42 = Hat(
            "Bolo",
            listOf(""),
            "http://textures.minecraft.net/texture/8b5851bcce8bfad39a47e50345c1d83a1de7b22509dc937ec7b9cd5150284954",
            "rmcosmetics.hat.bolo"
        )
        val chapeu43 = Hat(
            "Torta",
            listOf(""),
            "http://textures.minecraft.net/texture/487d562ec0c021347afb6811586047853da10f6fa0ea51d575fd341451ed0ebf",
            "rmcosmetics.hat.torta"
        )
        val chapeu44 = Hat(
            "Soldado Infernal",
            listOf(""),
            "http://textures.minecraft.net/texture/8ef0cd98c16f7f6c5d3cdcd87c5eab10da1909007c35d28bd5227d925ae5491a",
            "rmcosmetics.hat.soldado_infernal"
        )
        val chapeu45 = Hat(
            "Jack o'Lantern",
            listOf(""),
            "http://textures.minecraft.net/texture/4144aac2a2a459277b20d0f42c5e8b5f665cddbeed6ab6eb18f0d922a46de392",
            "rmcosmetics.hat.jack_lantern"
        )
        val chapeu46 = Hat(
            "Câmera Dourada",
            listOf(""),
            "http://textures.minecraft.net/texture/bd763a6508ac0fbaac8dfffe805fd7605a243e63ba7820a4c19b75a83e4bd3e",
            "rmcosmetics.hat.camera_dourada"
        )
        val chapeu47 = Hat(
            "Fardo de Feno",
            listOf(""),
            "http://textures.minecraft.net/texture/c10f9000936ec8ed51d1bf5a2c21a0493a43930a336a4120f95b8d748231a0f6",
            "rmcosmetics.hat.fardo_de_feno"
        )
        val chapeu48 = Hat(
            "Ovo Azul",
            listOf(""),
            "http://textures.minecraft.net/texture/b154ba42d2822f56534e755827892e4719deef328abb59584be6967f25f48cb4",
            "rmcosmetics.hat.ovo_azul"
        )
        val chapeu49 = Hat(
            "Ovo Rosa",
            listOf(""),
            "http://textures.minecraft.net/texture/eceddc23f9d96baaa0d2d7b9ec180cd7bea5447d39c425ca9e44d8808ea11ea0",
            "rmcosmetics.hat.ovo_rosa"
        )
        val chapeu50 = Hat(
            "Ovo Laranja",
            listOf(""),
            "http://textures.minecraft.net/texture/fd6b606ee35552b223f137639915c9625335aa4b9d09d6c0c17bb75d2db4f09",
            "rmcosmetics.hat.ovo_laranja"
        )
        val chapeu51 = Hat(
            "Baby Yoda",
            listOf(""),
            "http://textures.minecraft.net/texture/a4410d4032103b0755fb01679109f129911822b47bbe4b533b2561c77fcfd0af",
            "rmcosmetics.hat.baby_yoda"
        )
        val chapeu52 = Hat(
            "Gnomo",
            listOf(""),
            "http://textures.minecraft.net/texture/4936d79e4fda859d608c853c10ed42761b9379920b00b155433c8293f91b3e6f",
            "rmcosmetics.hat.gnomo"
        )
        val chapeu53 = Hat(
            "Olho do Diabo",
            listOf(""),
            "http://textures.minecraft.net/texture/7eb932356764a4750abf32e19725027ed38b31d917d15a7e3218abacc96058",
            "rmcosmetics.hat.olho_do_demonio"
        )
        val chapeu54 = Hat(
            "Enderbrine",
            listOf(""),
            "http://textures.minecraft.net/texture/1dffe1b60415046e694f69b2266bd30d13d8b41463867f84f0ff056eb64407cb",
            "rmcosmetics.hat.enderbrine"
        )
        val chapeu55 = Hat(
            "Diabinha",
            listOf(""),
            "http://textures.minecraft.net/texture/bab8f8e72fdaee2caf7220c438056a7fb7363edac791750a221bdb914d625cb6",
            "rmcosmetics.hat.diabinha"
        )
        val chapeu56 = Hat(
            "Gato de Botas",
            listOf(""),
            "http://textures.minecraft.net/texture/1d2410f6df8a72deaafc18b7d610567d7619b3ae2aa66befb55d8aab8550ee59",
            "rmcosmetics.hat.gato_de_botas"
        )
        val chapeu57 = Hat(
            "Shrek",
            listOf(""),
            "http://textures.minecraft.net/texture/4868dd50bcb73c47c4aaee75c7eb3c7097885d4a9dd34a57c8ca48de3b76598a",
            "rmcosmetics.hat.shrek"
        )
        val chapeu58 = Hat(
            "Null Texture",
            listOf(""),
            "http://textures.minecraft.net/texture/7c603c79560319996d6394128b49fec7650cf867a1e48fb80c6043e770dd71bd",
            "rmcosmetics.hat.null_texture"
        )
        val chapeu59 = Hat(
            "Time Apple",
            listOf(""),
            "http://textures.minecraft.net/texture/8cf811fa109cde629c712ccd3fc9289e678a5e18894f488c074448e48d852",
            "rmcosmetics.hat.time_apple"
        )
        val chapeu60 = Hat(
            "Minério de Creeper",
            listOf(""),
            "http://textures.minecraft.net/texture/ddc367acef8b96bc3b8e5cae6cf438fb5d5bb287fef018b149506148a6669adb",
            "rmcosmetics.hat.minerio_de_creeper"
        )
        val chapeu61 = Hat(
            "Bola de Futebol",
            listOf(""),
            "http://textures.minecraft.net/texture/8e4a70b7bbcd7a8c322d522520491a27ea6b83d60ecf961d2b4efbbf9f605d",
            "rmcosmetics.hat.bola_de_futebol"
        )
        val chapeu62 = Hat(
            "Zeus",
            listOf(""),
            "http://textures.minecraft.net/texture/17472c868a19a8cc606611c36f39eb1ba6697771f1daf98c4a72655e1069549c",
            "rmcosmetics.hat.zeus"
        )
        val chapeu63 = Hat(
            "Dragão do Mundo da Superfície",
            listOf(""),
            "http://textures.minecraft.net/texture/66cee4565f7bb287dc391ba7fa53a1fcb078a8c1967dbebd18d6519648beb3dd",
            "rmcosmetics.hat.dragao_do_mundo_da_superficie"
        )
        val chapeu64 = Hat(
            "Dragão do Nether",
            listOf(""),
            "http://textures.minecraft.net/texture/ab6e5cd612c73cd8e6c7a720c26283e7548ca728d8b30601840f7da5d433d83b",
            "rmcosmetics.hat.dragao_do_nether"
        )
        val chapeu65 = Hat(
            "Dragão do Fim",
            listOf(""),
            "http://textures.minecraft.net/texture/ead7dcbe93401b3d3b4e5222ef8097aef4431f48064d0a0531fa0952c211e0e6",
            "rmcosmetics.hat.dragao_do_fim"
        )
        val chapeu66 = Hat(
            "Talismã",
            listOf(""),
            "http://textures.minecraft.net/texture/317b51e086f201448a4b45b0b91e97faf4d1739071480be6d5cab0a054512164",
            "rmcosmetics.hat.talisma"
        )
        val chapeu67 = Hat(
            "Céu e Nuvens",
            listOf(""),
            "http://textures.minecraft.net/texture/1c0d5e7084d3b5bd49e17da03f00597c8fd271051b7660a7253412444803766f",
            "rmcosmetics.hat.ceu_e_nuvens"
        )
        val chapeu68 = Hat(
            "Orgulho LGBTQIA+",
            listOf(
                "",
                "§6Chapéu especial em homenagem ao Dia",
                "§6Internacional do Orgulho LGBTQIA+.",
                "",
            ),
            "http://textures.minecraft.net/texture/124932bb949d0c65717b121c8cd9a21b69e86c0f7e3421ae9b8c64d8b990eb60",
            "rmcosmetics.hat.orgulho_lgbtqia+"
        )
        val chapeu69 = Hat(
            "BB-8",
            listOf(""),
            "http://textures.minecraft.net/texture/42d271f82674916dcb47b372a572f61e7472fe063b2c9246aeeb7c57a8835",
            "rmcosmetics.hat.bb-8"
        )
        val chapeu70 = Hat(
            "Gnomo",
            listOf(""),
            "http://textures.minecraft.net/texture/4936d79e4fda859d608c853c10ed42761b9379920b00b155433c8293f91b3e6f",
            "rmcosmetics.hat.gnomo"
        )
        val chapeu71 = Hat(
            "Máscara de Zumbi",
            listOf(""),
            "http://textures.minecraft.net/texture/143679688d6266a9679b38e5b5e3c60aac644f61eef2750b0605418342682c9",
            "rmcosmetics.hat.mascara_de_zumbi"
        )
        val chapeu72 = Hat(
            "Chapéu de Aranha",
            listOf(""),
            "http://textures.minecraft.net/texture/9964832a667388ba3f5c895d504218f2352efa1af9d7ff7ba10339decd8f15",
            "rmcosmetics.hat.mascara_de_aranha"
        )
        val chapeu73 = Hat(
            "Máscara de Esqueleto",
            listOf(""),
            "http://textures.minecraft.net/texture/c79e723491c886dcc25e5d2df0e05781f7ac9c34621832bd74965ef79acf8f",
            "rmcosmetics.hat.mascara_de_esqueleto"
        )
        val chapeu74 = Hat(
            "Máscara de Ovelha",
            listOf(""),
            "http://textures.minecraft.net/texture/1d73f0dde41568c73f252a22f36bedcd43e033c59517a7ee8f9a31319972b27",
            "rmcosmetics.hat.mascara_de_ovelha"
        )
        val chapeu75 = Hat(
            "Máscara do Herobrine",
            listOf(""),
            "http://textures.minecraft.net/texture/ab9b122bf9efcf2afeaa9dad2d692558363a69d7f3912dabc5672dfceaeb7ce",
            "rmcosmetics.hat.mascara_do_herobrine"
        )
        val chapeu76 = Hat(
            "Máscara de Guardião",
            listOf(""),
            "http://textures.minecraft.net/texture/a6abb8d88a97d1c2bbf4e69dc62c2b1a83de925f133f7482e89089f682987386",
            "rmcosmetics.hat.mascara_de_guardiao"
        )
        val chapeu77 = Hat(
            "Máscara de Enderman",
            listOf(""),
            "http://textures.minecraft.net/texture/d746c26aac16e3880982d1399fb5f9f79913dd616e71654f9bf4d41fb33",
            "rmcosmetics.hat.mascara_de_enderman"
        )
        val chapeu78 = Hat(
            "Máscara de Vaca",
            listOf(""),
            "http://textures.minecraft.net/texture/1b34106cc2eac979349d5bc6a010534726f73fb960c228a8d91897642bed0b0",
            "rmcosmetics.mascara_de_vaca"
        )
        val chapeu79 = Hat(
            "Máscara de Bruxa",
            listOf(""),
            "http://textures.minecraft.net/texture/83bee9f9b75ff75197e07414ac2db92e216e945d6187722bdb9262879bb6",
            "rmcosmetics.mascara_de_bruxa"
        )
        val chapeu80 = Hat(
            "Máscara de Galinha",
            listOf(""),
            "http://textures.minecraft.net/texture/c4f0a16a2c8732d15767340dd7f813158c57e1240d5d233c87d15a4669b46d",
            "rmcosmetics.hat.mascara_de_galinha"
        )
        val chapeu81 = Hat(
            "Atendente do McDonald's",
            listOf(""),
            "http://textures.minecraft.net/texture/cf2f1f1be26da5af0088c5f990662e9733cfd64f8cd6fec9b630eac42b021d51",
            "rmcosmetics.hat.atendente_do_macdonalds"
        )
        val chapeu82 = Hat(
            "Xbox One (Branco)",
            listOf(""),
            "http://textures.minecraft.net/texture/97b00465188f6883a1b7a3b779e7d57334b9d2d11629e111b0de06270ef28442",
            "rmcosmetics.hat.xbox_one_branco"
        )
        val chapeu83 = Hat(
            "Xbox One (Preto)",
            listOf(""),
            "http://textures.minecraft.net/texture/e312468de4ca8fe9a8d82aa25dfad812f247a699f964471adbaebc9d26b785c1",
            "rmcosmetics.hat.xbox_one_preto"
        )

        hats.add(chapeu1)
        hats.add(chapeu2)
        hats.add(chapeu3)
        hats.add(chapeu4)
        hats.add(chapeu5)
        hats.add(chapeu6)
        hats.add(chapeu7)
        hats.add(chapeu8)
        hats.add(chapeu9)
        hats.add(chapeu10)
        hats.add(chapeu11)
        hats.add(chapeu12)
        hats.add(chapeu13)
        hats.add(chapeu14)
        hats.add(chapeu15)
        hats.add(chapeu16)
        hats.add(chapeu17)
        hats.add(chapeu18)
        hats.add(chapeu19)
        hats.add(chapeu20)
        hats.add(chapeu21)
        hats.add(chapeu22)
        hats.add(chapeu23)
        hats.add(chapeu24)
        hats.add(chapeu25)
        hats.add(chapeu26)
        hats.add(chapeu27)
        hats.add(chapeu28)
        hats.add(chapeu29)
        hats.add(chapeu30)
        hats.add(chapeu31)
        hats.add(chapeu32)
        hats.add(chapeu33)
        hats.add(chapeu34)
        hats.add(chapeu35)
        hats.add(chapeu36)
        hats.add(chapeu37)
        hats.add(chapeu38)
        hats.add(chapeu39)
        hats.add(chapeu40)
        hats.add(chapeu41)
        hats.add(chapeu42)
        hats.add(chapeu43)
        hats.add(chapeu44)
        hats.add(chapeu45)
        hats.add(chapeu46)
        hats.add(chapeu47)
        hats.add(chapeu48)
        hats.add(chapeu49)
        hats.add(chapeu50)
        hats.add(chapeu51)
        hats.add(chapeu52)
        hats.add(chapeu53)
        hats.add(chapeu54)
        hats.add(chapeu55)
        hats.add(chapeu56)
        hats.add(chapeu57)
        hats.add(chapeu58)
        hats.add(chapeu59)
        hats.add(chapeu60)
        hats.add(chapeu61)
        hats.add(chapeu62)
        hats.add(chapeu63)
        hats.add(chapeu64)
        hats.add(chapeu65)
        hats.add(chapeu66)
        hats.add(chapeu67)
        hats.add(chapeu68)
        hats.add(chapeu69)
        hats.add(chapeu70)
        hats.add(chapeu71)
        hats.add(chapeu72)
        hats.add(chapeu73)
        hats.add(chapeu74)
        hats.add(chapeu75)
        hats.add(chapeu76)
        hats.add(chapeu77)
        hats.add(chapeu78)
        hats.add(chapeu79)
        hats.add(chapeu80)
        hats.add(chapeu81)
        hats.add(chapeu82)
        hats.add(chapeu83)
    }
}