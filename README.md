# Shogi

A fully playable implementation of Shogi (Japanese Chess) written in Java, featuring both a console interface and a graphical client built with OpenGL.

## About the Game

Shogi is a two-player strategy board game native to Japan. It is played on a 9x9 grid and shares common ancestry with Chess. The defining feature that sets Shogi apart is the drop rule: pieces captured from the opponent are held in hand and can be re-entered onto the board as one's own on any subsequent turn.

This project implements the complete ruleset including piece movement, promotion zones, forced and optional promotion, capturing, drops from hand, check detection, checkmate, and stalemate.

## Features

- Full Shogi ruleset with all 14 piece types including promoted variants
- Piece promotion — forced where required, optional with player confirmation
- Drop system — captured pieces are demoted and held in hand, selectable and droppable onto valid squares
- Legal move validation including check prevention
- Checkmate and stalemate detection
- Console mode for testing and development
- Graphical mode with spritesheet-based rendering, animated piece selection, move highlighting, and a hand panel

## Project Structure

```
shogi/
  controller/   Game loop and state management
  core/         Coordinates, moves, input handling, drop validation
  model/        Board, Hand, and all piece implementations
  view/         Console renderer and OpenGL graphical interface
  res/          Sprites and bitmap font assets
```

## Technologies

**Java 24** — core language.

**LWJGL (Lightweight Java Game Library)** — provides OpenGL bindings and window/input management for the graphical client.

**lwjgl-basics** — a lightweight sprite batch and bitmap font rendering library built on top of LWJGL, used for 2D rendering of the board, pieces, panels, and UI elements.

**BMFont format** — bitmap font descriptor format used to render in-game text with a custom typeface.

## Architecture Notes

The project follows a clean object-oriented architecture. `Piece` subclasses define their own movement vectors, promotion rules, and sprite mappings. `Board` manages state and delegates move execution. The graphical and console game loops share a common `Game` base class. UI components (`BoardPanel`, `LeftPanel`, `PromotionDialog`) are self-contained and communicate only through the renderer.

## Running the Project

The project requires Java 17 or higher and the LWJGL native libraries for your operating system. Import into Eclipse or IntelliJ, ensure the `res` folder is marked as a source root, and run `Main.java`.
