package com.example.memorinak

import android.os.Bundle
import android.os.Handler
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var firstCard: ImageView? = null
    private var secondCard: ImageView? = null
    private var isProcessing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val numRows = 4
        val numCols = 4
        val totalCards = numRows * numCols

        val gridLayout = GridLayout(this).apply {
            rowCount = numRows
            columnCount = numCols
        }

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        val cardWidth = screenWidth / numCols
        val cardHeight = screenHeight / numRows

        val cardFaces = listOf(
            R.drawable.card_1,
            R.drawable.card_2,
            R.drawable.card_3,
            R.drawable.card_4,
            R.drawable.card_5,
            R.drawable.card_6,
            R.drawable.card_7,
            R.drawable.card_8
        )
        val cards = (cardFaces + cardFaces).shuffled()

        for (i in 0 until totalCards) {
            val card = ImageView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = cardWidth
                    height = cardHeight
                }
                setImageResource(R.drawable.card_back)
                tag = cards[i]
                setOnClickListener { onCardClicked(this) }
            }
            gridLayout.addView(card)
        }

        setContentView(gridLayout)
    }

    private fun onCardClicked(card: ImageView) {
        if (isProcessing || card == firstCard || card == secondCard) return

        val cardFace = card.tag as Int
        card.setImageResource(cardFace)

        when {
            firstCard == null -> {
                firstCard = card
            }
            secondCard == null -> {
                secondCard = card
                checkMatch()
            }
        }
    }

    private fun checkMatch() {
        if (firstCard == null || secondCard == null) return

        isProcessing = true

        val handler = Handler()
        if (firstCard?.tag == secondCard?.tag) {

            handler.postDelayed({
                firstCard?.visibility = ImageView.INVISIBLE
                secondCard?.visibility = ImageView.INVISIBLE
                resetSelection()
            }, 1000)
        } else {
            handler.postDelayed({
                firstCard?.setImageResource(R.drawable.card_back)
                secondCard?.setImageResource(R.drawable.card_back)
                resetSelection()
            }, 1000)
        }
    }

    private fun resetSelection() {
        firstCard = null
        secondCard = null
        isProcessing = false // Разрешаем действия
    }
}
