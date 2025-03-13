package com.microservices.assignment02.domain;

import com.microservices.assignment02.dto.GameDTO;
import com.microservices.assignment02.dto.PlayerDTO;
import com.microservices.assignment02.dto.ShipDTO;
import com.microservices.assignment02.entity.GameEntity;
import com.microservices.assignment02.entity.PlayerEntity;
import com.microservices.assignment02.entity.ShipEntity;
import com.microservices.assignment02.rest.GameController;
import com.microservices.assignment02.rest.ShipController;
import com.microservices.assignment02.service.GameService;
import com.microservices.assignment02.service.PlayerService;
import com.microservices.assignment02.service.ShipService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class LogicController {
    private final GameService gameService;
    private final PlayerService playerService;
    private final ShipService shipService;
    private final GameController gameController;
    private final ShipController shipController;
    private final Scanner scanner;

    public LogicController(GameService gameService, PlayerService playerService, ShipService shipService,  GameController gameController, ShipController shipController) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.shipService = shipService;
        this.scanner = new Scanner(System.in);
        this.gameController = gameController;
        this.shipController = shipController;
    }

    public void startGame() {
        System.out.println("Welcome to Battleship Game!");
        System.out.println("==========================");

        boolean playing = true;
        while (playing) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Create a new game");
            System.out.println("2. Join an existing game");
            System.out.println("3. List all games");
            System.out.println("4. Exit");
            System.out.print("Choose an option (1-4): ");

            int choice = getNumericInput();
            switch (choice) {
                case 1:
                    createNewGame();
                    break;
                case 2:
                    joinExistingGame();
                    break;
                case 3:
                    listAllGames();
                    break;
                case 4:
                    playing = false;
                    System.out.println("Thanks for playing! Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void createNewGame() {
        System.out.print("Enter game name: ");
        String gameName = scanner.nextLine();

        GameDTO gameDTO = new GameDTO(gameName);
        GameEntity game = gameService.saveGame(new GameEntity(gameDTO.getName()));

        System.out.println("Game created successfully with ID: " + game.getId());

        setupPlayersAndShips(game);
    }

    private void joinExistingGame() {
        listAllGames();
        System.out.print("Enter the ID of the game you want to join: ");
        Long gameId = (long) getNumericInput();

        try {
            GameEntity game = gameService.getGameById(gameId);
            System.out.println("Joining game: " + game.getName());

            if (game.getPlayers().size() >= 2) {
                System.out.println("This game already has 2 players. You can only spectate.");
                displayGameStatus(gameId);
                return;
            }

            setupPlayersAndShips(game);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void setupPlayersAndShips(GameEntity game) {
        int playerCount = game.getPlayers().size();

        if (playerCount < 2) {
            System.out.print("Enter your name: ");
            String playerName = scanner.nextLine();

            PlayerDTO playerDTO = new PlayerDTO(playerName, game.getId());
            PlayerEntity player = playerService.createPlayer(playerDTO.getName(), playerDTO.getGameId());

            System.out.println("Player created with ID: " + player.getId());

            setupShips(player);

            if (playerCount == 0) {
                System.out.println("Waiting for another player to join...");
            } else {
                System.out.println("Both players have joined. Game is ready to start!");
                playGame(game.getId());
            }
        }
    }

    private void setupShips(PlayerEntity player) {
        System.out.println("\nPlace your ships on a 10x10 grid (coordinates 0-9)");
        System.out.println("You need to place 5 ships of size 1.");

        List<String> positions = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            System.out.print("Enter coordinates for ship " + (i + 1) + " (e.g., '25' for row 2, column 5): ");
            String position = scanner.nextLine();

            while (position.length() != 2 || !Character.isDigit(position.charAt(0)) || !Character.isDigit(position.charAt(1))) {
                System.out.print("Invalid format. Please enter two digits (e.g., '25'): ");
                position = scanner.nextLine();
            }

            positions.add(position);
        }

        ShipDTO shipDTO = new ShipDTO(player.getId(), positions);
        List <ShipEntity> response = shipService.placeShips(player.getId(),  positions );
        System.out.println(response);
    }

    private void playGame(Long gameId) {
        GameEntity game = gameService.getGameById(gameId);
        boolean gameFinished = false;

        while (!gameFinished) {
            displayGameStatus(gameId);

            PlayerEntity currentPlayer = game.getCurrentTurn();
            System.out.println("\nIt's " + currentPlayer.getName() + "'s turn!");

            System.out.print("Enter coordinates to shoot (e.g., '25' for row 2, column 5): ");
            String shotPosition = scanner.nextLine();

            while (shotPosition.length() != 2 || !Character.isDigit(shotPosition.charAt(0)) || !Character.isDigit(shotPosition.charAt(1))) {
                System.out.print("Invalid format. Please enter two digits (e.g., '25'): ");
                shotPosition = scanner.nextLine();
            }

            int x = Character.getNumericValue(shotPosition.charAt(0));
            int y = Character.getNumericValue(shotPosition.charAt(1));

            String response = String.valueOf(shipController.shoot(currentPlayer.getId(), gameId, x, y));
            System.out.println(response);

            game = gameService.getGameById(gameId);
            gameFinished = game.isFinished();

            if (!gameFinished) {
                System.out.print("Press Enter for next turn...");
                scanner.nextLine();
            }
        }

        displayGameStatus(gameId);
        System.out.println("\nGame Over!");
    }

    private void displayGameStatus(Long gameId) {
        try {
            String status = String.valueOf(gameController.getGameStatus(gameId));
            System.out.println("\n---- Game Status ----");
            System.out.println(status);
            System.out.println("--------------------");
        } catch (Exception e) {
            System.out.println("Error retrieving game status: " + e.getMessage());
        }
    }

    private void listAllGames() {
        List<GameEntity> games = gameService.getAllGames();

        if (games.isEmpty()) {
            System.out.println("No games found.");
            return;
        }

        System.out.println("\n---- Available Games ----");
        for (GameEntity game : games) {
            System.out.printf("ID: %d | Name: %s | Active: %s | Players: %d%n",
                    game.getId(),
                    game.getName(),
                    game.isActive(),
                    game.getPlayers().size());
        }
        System.out.println("------------------------");
    }

    private int getNumericInput() {
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}