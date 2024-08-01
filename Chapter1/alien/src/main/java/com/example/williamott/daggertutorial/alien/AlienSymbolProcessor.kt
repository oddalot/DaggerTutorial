package com.example.williamott.daggertutorial.alien

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

class AlienSymbolProcessor(
    private val logger: KSPLogger
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val injectSymbols = resolver.getSymbolsWithAnnotation("com.example.williamott.daggertutorial.alien.AlienInject")
        injectSymbols.forEach { symbol ->
            symbol.accept(InjectSymbolVisitor(logger), Unit)
        }

        return emptyList()
    }
}