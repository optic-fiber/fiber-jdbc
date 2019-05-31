package com.cheroliv.fiber.service

interface InterService {
    void processClasseurFeuilles()

    void saveToJsonFile(String path) throws IOException

    void importFromJsonFile(String path) throws IOException

    void setUp()

    String getFiberJsonFilePath()
}