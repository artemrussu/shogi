
---

## Roadmap

### Phase 1 — Quick Wins (Independent, Fast)

* **Sound:** Click, capture, promotion, game over — TinySound / OpenAL
* **Escape menu:** Pause → New Game / Settings / Exit
* **Timer:** Move timer and game clock — integration into Game

### Phase 2 — Move Notation (Critical Dependency for Next Steps)

* **USI/KIF notation:** Every move → string format like `"7g7f"`, `"B*5e"`, `"7g7f+"`
*Required for: move history, AI, online play*

### Phase 3 — Right Panel

* **Move history:** List of moves in KIF notation — *depends on Phase 2*

### Phase 4 — AI

* **USI integration:** Process + stdin/stdout → YaneuraOu / Apery
*depends on Phase 2 (notation for communicating with the engine)*
* **Engine analysis:** Best moves in the right panel — *depends on Phase 3 + Phase 4*

### Phase 5 — Online

* **Server:** Netty / raw sockets
* **Matchmaking:** *depends on the stability of Phases 1–4*

### Phase 6 — Polish

* **Visuals:** Animations, effects
* **Settings:** Audio, resolution, AI difficulty

---

### Dependency Flow

```text
Phase 1    Sound + Escape menu + Timer
    ↓
Phase 2    Move notation (USI/KIF)          ← Everything depends on this
    ↓
Phase 3    Right panel history
    ↓
Phase 4    AI (USI) + Engine analysis
    ↓
Phase 5    Online
    ↓
Phase 6    Polish

```