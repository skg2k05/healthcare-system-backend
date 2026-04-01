import { readFileSync } from "node:fs";

const filePath = new URL("../src/pages/CitizenDashboard.jsx", import.meta.url);
const content = readFileSync(filePath, "utf8");

if (/<<<<<<<|=======|>>>>>>>/.test(content)) {
  console.error("[prebuild] Merge conflict markers found in CitizenDashboard.jsx. Resolve conflicts before deploy.");
  process.exit(1);
}

console.log("[prebuild] CitizenDashboard conflict-marker check passed.");
