package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.MoLangParser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.TokenIterator;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.utils.FileUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MoLang {
    public static List<Expression> parse(String code) {
        return MoLang.createParser(code).parse();
    }

    public static List<Expression> parse(Path path) {
        return MoLang.createParser(path).parse();
    }

    public static List<Expression> parse(InputStream stream) throws IOException {
        return MoLang.createParser(stream).parse();
    }

    public static MoLangParser createParser(String code) {
        return new MoLangParser(new TokenIterator(code), code);
    }

    public static MoLangParser createParser(Path path) {
        byte[] fileBytes;
        try {
            fileBytes = Files.readAllBytes(path);
        }
        catch (IOException e) {
            fileBytes = new byte[]{};
        }
        String code = new String(fileBytes, StandardCharsets.UTF_8);
        return MoLang.createParser(code);
    }

    public static MoLangParser createParser(InputStream stream) throws IOException {
        String code = FileUtils.readFile(stream);
        return MoLang.createParser(code);
    }

    public static MoLangRuntime createRuntime() {
        return new MoLangRuntime();
    }
}

