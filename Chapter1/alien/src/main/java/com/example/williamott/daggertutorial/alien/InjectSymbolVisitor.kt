package com.example.williamott.daggertutorial.alien

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid

class InjectSymbolVisitor(
    private val logger: KSPLogger
)  : KSVisitorVoid() {
    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
        logger.warn("@AlienInject found on ${property.simpleName.getShortName()} property")
    }
}