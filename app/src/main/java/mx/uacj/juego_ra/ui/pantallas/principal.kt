package mx.uacj.juego_ra.ui.pantallas

import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mx.uacj.juego_ra.modelos.Informacion
import mx.uacj.juego_ra.modelos.InformacionInteractiva
import mx.uacj.juego_ra.modelos.TiposDePistas
import mx.uacj.juego_ra.repositorios.estaticos.RepositorioPruebas
import mx.uacj.juego_ra.ui.organismos.InformacionInteractivaVista
import mx.uacj.juego_ra.ui.organismos.InformacionVista

@Composable
fun Principal(ubicacion: Location?, modificador: Modifier = Modifier){

    var mostrar_pantalla_generica by remember { mutableStateOf(true) }
    var mostrar_pista_cercana by remember { mutableStateOf(false) }

    Column(
        modifier = modificador
            .fillMaxSize()
            .background(color = Color(0xFFE8E9F3.toLong())),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        for (pista in RepositorioPruebas.pistas) {
            if (ubicacion == null) {
                break
            }

            var distancia_a_la_pista = ubicacion.distanceTo(pista.ubicacion)

            if (distancia_a_la_pista < pista.distancia_maxima) {
                mostrar_pantalla_generica = false
                var nivel_de_distancia =
                    (distancia_a_la_pista * 100) / (pista.distancia_maxima - pista.distancia_minima)

                Column(
                    modifier = Modifier

                        .fillMaxWidth(0.8f)
                        .padding(vertical = 12.dp) // 🔹 espacio entre tarjetas
                        .padding(8.dp)
                        .background(
                            color = Color(0xFF474056.toLong()),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(8.dp)

                ) {
                    Text("La pista es: ${pista.nombre}",
                        fontSize = 25.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8A95A5.toLong())

                    )
                    Text("el nivel de la distancia a la pista es ${nivel_de_distancia}",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF757083.toLong())
                    )

                    if (nivel_de_distancia > 75) {
                        Text("Estas frio todavia",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFB8D4E3.toLong()),
                        )
                    } else if (nivel_de_distancia > 50) {
                        Text("Te estas acercando",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFDB504A.toLong()),)
                    } else if (nivel_de_distancia > 25) {
                        Text("Muy cercas, sigue asi",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF93032E.toLong()),
                        )
                    } else if (nivel_de_distancia < 20 && !mostrar_pista_cercana) {
                        Row(modifier = Modifier.fillMaxWidth().clickable {
                            mostrar_pista_cercana = true
                        }) {
                            Text("Capturar pista cercana")
                        }
                    }


                    if (mostrar_pista_cercana) {
                        when (pista.cuerpo.tipo) {
                            TiposDePistas.texto -> {
                                InformacionVista(pista.cuerpo as Informacion)
                            }

                            TiposDePistas.interactiva -> {
                                InformacionInteractivaVista(pista.cuerpo as InformacionInteractiva)
                            }

                            TiposDePistas.camara -> {
                                TODO()
                            }

                            TiposDePistas.agitar_telefono -> {
                                TODO()
                            }
                        }
                    }
                }
            }
        }

    }

    if(mostrar_pantalla_generica){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    color = Color(0xFFE8E9F3.toLong()),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp)
        ) {
            Text("NO te encuentras cerca de alguna pista por el momento",
                fontSize = 25.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF93032E.toLong()),
            )
            Text("Por favor sigue explorando",
                fontSize = 25.sp,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Black,
                color = Color(0xFF3A435E.toLong()),
            )
            }

        }

}