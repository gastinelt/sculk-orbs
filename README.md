# Sculk Orbs

Mod NeoForge pour **Minecraft 1.21.1**. Miner du bloc de sculk peut faire tomber des **orbes d'expérience** de cinq rangs, et des objets d'**enchantement parfait** s'appliquent à l'enclume.

## Orbes

Chaque bloc de sculk miné (sans toucher de soie, hors mode créatif) donne **un seul orbe**, tiré au hasard. Un clic droit sur l'orbe donne de l'expérience.

| Orbe | Expérience | Probabilité du tirage |
|---|---|---|
| Massif | 75 | 25 % |
| Géant | 350 | 25 % |
| Colossal | 1 500 | 25 % |
| Mythique | 6 000 | 20 % |
| Maximal | 30 000 | 5 % |

Avec le toucher de soie, le bloc de sculk tombe normalement. Textes en français et en anglais.

## Enchantements parfaits

Cinq objets « enchantement parfait » (niveaux 1 à 5), à combiner à l'enclume avec un objet. Ils appliquent à l'objet les enchantements adaptés à son type, au niveau de l'objet utilisé, dans la limite du niveau maximal de chaque enchantement. Pour un objet sans liste dédiée mais enchantable ou qui s'use, c'est Solidité et Raccommodage.

## Compiler

Prérequis : JDK 21.

```bash
./gradlew build
```

Le fichier `.jar` est produit dans `build/libs/`.

## Licence

[MIT](LICENSE).
