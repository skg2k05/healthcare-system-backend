import { readFileSync } from "node:fs";

const filePath = new URL("../src/pages/CitizenDashboard.jsx", import.meta.url);
const content = readFileSync(filePath, "utf8");

if (/<<<<<<<|=======|>>>>>>>/.test(content)) {
  console.error("[prebuild] Merge conflict markers found in CitizenDashboard.jsx. Resolve conflicts before deploy.");
  process.exit(1);
}

if (content.includes("</label>")) {
  console.error(
    "[prebuild] Unexpected </label> detected in CitizenDashboard.jsx. Please keep the latest fixed form markup before deploying."
  );
  process.exit(1);
}

console.log("[prebuild] CitizenDashboard sanity checks passed.");
