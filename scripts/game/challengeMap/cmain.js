//Will be added in version 1.4 or 1.3.30 onwards
const challenge = new JavaAdapter(Planet, {
    load() {
        this.meshLoader = prov(() => new HexMesh(challenge, 6));
        this.super$load();
    }
}, "challenge", Planets.sun, 3, 1);
challenge.generator = new SerpuloPlanetGenerator();
challenge.atmosphereColor = Color.valueOf("d31e1e");
challenge.atmosphereRadIn = 0.04;
challenge.atmosphereRadOut = 0.3;
challenge.startSector = 12;

const start = new SectorPreset("start", challenge, 12);
start.difficulty = 7;

exports.start = start;