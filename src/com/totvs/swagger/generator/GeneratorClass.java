package com.totvs.swagger.generator;

import java.io.File;
import java.io.IOException;

public class GeneratorClass {

	public void mainGenerator(String[] args) throws IOException {

		System.out.println(
				"Automa��o de Gera��o Swagger, favor informar o caminho de leitura e grava��o do arquivo de documenta��o.");

		String pathLeituraParam = "";
		String pathGravacaoParam = "";

		switch (args.length) {
		case 0:
			System.out.println("� preciso informar par�metro para leitura e grava��o do arquivo.");
			break;
		case 1:
			System.out.println("� preciso informar par�metro para leitura e grava��o do arquivo.");
			break;
		case 2:
			pathLeituraParam = args[0];
			pathGravacaoParam = args[1];
		default:
			break;
		}

		System.out.println("Path informado: " + pathLeituraParam);

		if (pathLeituraParam != null && !pathLeituraParam.isEmpty()) {

			File fileDirectories = new File(pathLeituraParam);

			if (!fileDirectories.isDirectory()) {
				System.out.println("O par�metro informado n�o corresponde a um diret�rio.");
				return;
			}

			if (!fileDirectories.canRead()) {
				System.out.println("Voc� n�o tem permiss�o para ler o diret�rio informado.");
				return;
			}

			File[] listFilesAPIs = fileDirectories.listFiles((dir, name) -> {
				return name.toLowerCase().endsWith(".yaml");
			});

			if (listFilesAPIs != null) {

				ValidateClass valida = new ValidateClass();

				System.out.println("Iniciando leitura dos arquivos do caminho informado.");

				for (File file : listFilesAPIs) {

					String conteudoYaml = "";
					conteudoYaml = new UtilClass().readFileToString(file.getPath());

					System.out.println("Iniciando valida��o do arquivo informado.");

					if (valida.validaFonteProgress(conteudoYaml, pathLeituraParam)) {

						System.out.println("Convertendo o arquivo YAML para JSON");
						String conteudoJson = "";
						conteudoJson = UtilClass.convertToJson(conteudoYaml);

						System.out.println("Gerando Arquivo JSON");
						new UtilClass().gravaJsonGerado(conteudoJson, pathGravacaoParam, file.getName());

						System.out.println("Arquivo gerado com sucesso");

					} else
						System.out
								.println("Foram encontrados problemas na gera��o do arquivo, processo ser� abortado.");
				}

			} else
				System.out.println("N�o foi encontrado nenhum arquivo .yaml para leitura.");

		}
	}

}
